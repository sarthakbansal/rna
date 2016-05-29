package com.rna.signuplogincontroller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.WebDataBinder;
//import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.rna.register.UserSignUp;
import com.rna.login.UserLogin;
import com.rna.db.dbOperations;
import com.rna.variables.variables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class SignUpLoginController {

	private dbOperations db;
	private variables var;
	// login page mapping
	@RequestMapping(value="/login", method = RequestMethod.GET )
	
	public ModelAndView loginForm() {

		ModelAndView model1 = new ModelAndView("LoginForm");
		
		return model1;
	}
	
	//Check login credentials
	@RequestMapping(value="/checkLogin", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Map<String,String>> checkLogin(HttpServletRequest request) {
		 
		db = new dbOperations();
		var = new variables();
		
		
		var.userName = request.getParameter("username");
		var.password = request.getParameter("password");
		var.userType = request.getParameter("userType");
		 // read values and redirects to company page 
		 if(var.userType.equals("company"))
			 var.loginList = db.checkLogin(var.userName, var.password, var.userType, var.companyID);
		 else
			 var.loginList = db.checkLogin(var.userName, var.password, var.userType, var.transporterID);
			 
		 var.loginDataList = new ArrayList<Map<String,String>>();
		 Map map = new HashMap<String, String>();
		 
		 if(var.loginList.size()>0)
			 map.put("message", "success");
		 else
			 map.put("message", "Username or password is incorrect !");
		 
		 var.loginDataList.add(map);
		 return var.loginDataList;
	}
	//Mapping After login page submission
	@RequestMapping(value="/USER", method = RequestMethod.POST )
	public ModelAndView loginForm(@Valid @ModelAttribute("userLogin") UserLogin userLogin, BindingResult result) {

		 db = new dbOperations();
		 var = new variables();
		 
		 // read values and redirects to company page 
		 if(userLogin.getUserType().equals("company"))
		 {
			 var.loginList = db.checkLogin(userLogin.getUsername(),userLogin.getPassword(),userLogin.getUserType(),var.companyID);
			 
			 if(var.loginList.size()>0){
				 for(Map<String, String> map : var.loginList){
					 var.companyID = map.get("companyId");
					 var.userName = map.get("username");
				}
				
				// Gets source and truckType from the contract table for the particular company
		        var.sourceList = db.getSource("company",var.companyID);
				var.truckTypeList = db.getTruckType("company",var.companyID);
		    	
				//variables for company page 
				ModelAndView model1 = new ModelAndView("company");
				model1.addObject("companyId", var.companyID);
				model1.addObject("username", var.userName);
		    	if(var.sourceList.size()>0){
	    			model1.addObject("sourceList", var.sourceList);
	    		}
	    		if(var.truckTypeList.size()>0){
	    			model1.addObject("truckTypeList", var.truckTypeList);
	    		}
		    	return model1;
		    }
			
			// for invalid userName or login
		    else { 		    	
		    	ModelAndView model1 = new ModelAndView("LoginForm");
		    	model1.addObject("message", "Invalid Username or Password");
		    	return model1;
		    }
		 }
		 
		 // read values and redirects to transporter page 			
		 else
		 {
			 // fetching related lists from database tables 
			 var.loginList = db.checkLogin(userLogin.getUsername(),userLogin.getPassword(),userLogin.getUserType(),var.transporterID);
			 var.sourceList = db.getSource("transporter","0");
			 var.destinationList = db.getDestination("transporter","0");
			 var.truckTypeList = db.getTruckType("transporter","0");
			 Map data = new HashMap<String, String>();
			 
			 // adding objects for transporter page 
			 if(var.loginList.size()>0){
		        data = var.loginList.get(0);
		    	ModelAndView model1 = new ModelAndView("transporter", data);
		    	if(var.sourceList.size()>0){
	    			model1.addObject("sourceList", var.sourceList);
	    		}
	    		if(var.destinationList.size()>0){
	    			model1.addObject("destinationList", var.destinationList);
	    		}
	    		if(var.truckTypeList.size()>0){
	    			model1.addObject("truckTypeList", var.truckTypeList);
	    		}
		    	return model1;
		    }
			
			// for invalid transporter login values 
		    else {
		    	data.put("message", "Invalid Username or Password");
		    	ModelAndView model1 = new ModelAndView("LoginForm", data);
		    	return model1;
		    } 
		 }
		 
	}

	// Global header 
	@ModelAttribute
    public void addingCommonObjects(Model model1) {		
		
		model1.addAttribute("headerMessage", "RNA Logistics");
	}

	// SignUp page mapping
	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public ModelAndView signUpForm() {

		ModelAndView model1 = new ModelAndView("SignUpForm");
		return model1;
	}
	
	// after SignUp Success page mapping 
	@RequestMapping(value="/checkSignUp", method = RequestMethod.POST)
	public @ResponseBody ArrayList<Map<String,String>> checkSignUp(@Valid @ModelAttribute("userSignUp") UserSignUp userSignUp, BindingResult result, HttpServletRequest request) {

		db = new dbOperations();
		var = new variables();
		var.address = request.getParameter("address");
		System.out.println(var.address);
		
		var.signupDataList = new  ArrayList<Map<String,String>>();
		 Map data = new HashMap<String, String>();
		int flag = 0;
		var.userNameList = db.checkUserName(userSignUp);
	    if(var.userNameList.size()>0){
	    	data.put("error_message1", "Username already exists !");
	    	flag=1;
	    }
	    var.emailList = db.checkEmail(userSignUp);
	    if(var.emailList.size()>0){
	    		data.put("error_message2", "Email already exists !");
	    		flag=1;
	    	}
	    var.contactList = db.checkContact(userSignUp);
	    if(var.contactList.size()>0){
	    	data.put("error_message3", "Contact No. already exists !");
	    		flag=1;
	    }
	    if(flag==1)
	    	data.put("status","fail");
	    else{
	    	if(db.registerUser(userSignUp, var.address)){
		    	data.put("status","sucess");
		    	data.put("message","Signed up sucessfully !");
	    	}
	    	else{
	    		data.put("status","fail");
		    	data.put("message","There is some problem, Please try again !");
	    	}
	    }
	    var.signupDataList.add(data);
	    return var.signupDataList;
		
	}
	
	// Register truck for transporter page (AJAX Mapping)
	@RequestMapping(value="/registerTruck", method = RequestMethod.POST)
	public @ResponseBody String registerTruck(HttpServletRequest request) {
		
		var = new variables();
		db = new dbOperations();
		var.destinationName = request.getParameter("destinationName");
		var.sourceName = request.getParameter("sourceName");
		var.truckType = request.getParameter("trucktype");
		var.registerationNumber = request.getParameter("registerationNumber");
		var.driverName = request.getParameter("driver_name");
		var.driverContact = request.getParameter("driver_mobile");
		var.panNumber = request.getParameter("panNumber");
		var.userName = request.getParameter("userName");
		
		var.contractList = db.checkTruck(var.registerationNumber);
		if(var.contractList.size()>0)
			return "Above Truck is already registered !";
		else
		{
			if(db.registerTruck(var.destinationName, var.sourceName, var.truckType, var.registerationNumber, var.driverName, var.driverContact, var.panNumber, var.userName))
				return "Truck has been registered successfully !";
			else
				return "There is some problem, Please try again !";
		}			
	}
	
	//  Handles source request to get corresponding destination for a source in company page (AJAX Mapping)
	@RequestMapping(value="/getDestinationName", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getDestinationName(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.sourceName = request.getParameter("sourceName");
		var.companyID = request.getParameter("companyId");
		
		var.destinationList = db.getLocation(var.sourceID, var.destinationID, var.sourceName, var.companyID);	
		ModelAndView model1 = new ModelAndView("sourceDestLocation");
		model1.addObject("destinationList", var.destinationList);
	
		model1.addObject("location", "destination");
		return model1;
	}
	
    //  Fetches all address for a particular source/destination in company page (AJAX Mapping)
	@RequestMapping(value="/getLocationAddress", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getLocationAddress(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.location = request.getParameter("location");
		var.companyID = request.getParameter("companyId");
		
		var.locationAddressList = db.getLocAddress(var.location, var.companyID);	
		ModelAndView model1 = new ModelAndView("locationAddress");
		model1.addObject("locationAddressList", var.locationAddressList);
		return model1;
	}
	
	// For adding a new address at a location in company page (AJAX Mapping)
	@RequestMapping(value="/addLocationAddress", method = RequestMethod.POST)
	public @ResponseBody String addLocationAddress(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.location = request.getParameter("location");
		var.companyID = request.getParameter("companyId");
		var.address = request.getParameter("address");
		
		var.addressList = db.checkLocAddress(var.address, var.companyID);
		if(var.addressList.size() > 0)
			return "Above location is already present !";
		else{
			if(db.inserLocAddress(var.location, var.address, var.companyID))
				return "Location added successfully, Now chose the location from the list";
			else
				return "There is some problem in adding location, Please try again !";
		}
		
	}
	
	//Fetch truck-types for the corresponding route from contract table in company page (AJAX Mapping)
	@RequestMapping(value="/getTruckType", method = RequestMethod.POST)
	public @ResponseBody ModelAndView getTruckType(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.destinationName = request.getParameter("destinationName");
		var.sourceName = request.getParameter("sourceName");
		var.companyID = request.getParameter("companyId");
		
		var.truckTypeList = db.bookTruckType(var.companyID, var.sourceName, var.destinationName);	
		ModelAndView model1 = new ModelAndView("truckType");
		model1.addObject("truckTypeList", var.truckTypeList);
		return model1;
	}
	
	// For booking a truck in company page (AJAX Mapping)
	@RequestMapping(value="/bookTruck", method = RequestMethod.POST)
	public @ResponseBody String bookTruck(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.destinationName = request.getParameter("destinationName");
		var.sourceName = request.getParameter("sourceName");
		var.truckType = request.getParameter("trucktype");
		var.destinationAddress = request.getParameter("destinationAddress");
		var.sourceAddress = request.getParameter("sourceAddress");
		var.companyID = request.getParameter("companyId");
		var.bookingDate = request.getParameter("bookingDate");
		
		var.bookingList = db.checkBooking(var.sourceName, var.sourceAddress, var.destinationName, var.destinationAddress, var.truckType, var.bookingDate, var.companyID);
		if(var.bookingList.size()>0)
			return "1";
		else
		{
			if(db.bookTruck(var.sourceName, var.sourceAddress, var.destinationName, var.destinationAddress, var.truckType, var.bookingDate, var.companyID))
				return "Truck has been booked successfully !";
			else
				return "There is some problem, Please try again !";
		}			
	}
	
	// If same truck booked for that day only
	@RequestMapping(value="/forceBookTruck", method = RequestMethod.POST)
	public @ResponseBody String forcebookTruck(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.destinationName = request.getParameter("destinationName");
		var.sourceName = request.getParameter("sourceName");
		var.truckType = request.getParameter("trucktype");
		var.destinationAddress = request.getParameter("destinationAddress");
		var.sourceAddress = request.getParameter("sourceAddress");
		var.companyID = request.getParameter("companyId");
		var.bookingDate = request.getParameter("bookingDate");
		
		if(db.bookTruck(var.sourceName, var.sourceAddress, var.destinationName, var.destinationAddress, var.truckType, var.bookingDate, var.companyID))
			return "Truck has been booked successfully !";
		else
			return "There is some problem, Please try again !";		
	}
	
	// For viewing company bookings
	@RequestMapping(value="/viewCompanyBookings", method = RequestMethod.POST)
	public @ResponseBody ModelAndView viewCompanyBookings(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.companyID = request.getParameter("companyId");
		ModelAndView model1 = new ModelAndView("ViewBooking");
		
		var.bookingList = db.viewBooking(var.companyID);
		if(var.bookingList.size()>0)
			model1.addObject("bookingList", var.bookingList);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
	}
	 
	// Returns details of company 
	@RequestMapping(value="/ViewCompany", method = RequestMethod.POST)
	public @ResponseBody ModelAndView ViewCompany(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
		var.companyID = request.getParameter("companyId");
		ModelAndView model1 = new ModelAndView("ViewCompany");

		var.companyList = db.viewCompany(var.companyID);
		if(var.companyList.size()>0)
			model1.addObject("companyList", var.companyList);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
	}
	
	
	
	// view trucks of transporter
	@RequestMapping(value="/viewTransporterTrucks", method = RequestMethod.POST)
	public @ResponseBody ModelAndView viewTransporterTrucks(HttpServletRequest request) {

		db = new dbOperations();
		var = new variables();
		var.userName = request.getParameter("username");
		ModelAndView model1 = new ModelAndView("ViewTruck");
		
		var.truckList = db.displayTruck(var.userName);
		if(var.truckList.size()>0)
			model1.addObject("truckList", var.truckList);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
//		return list;
	}
	
}

