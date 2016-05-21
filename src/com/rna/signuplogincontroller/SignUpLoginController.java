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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
public class SignUpLoginController {

	/*@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, "studentName", new StudentNameEditor());
	}*/
	
	private dbOperations db;
	
	@RequestMapping(value="/login", method = RequestMethod.GET )
	public ModelAndView loginForm() {

		ModelAndView model1 = new ModelAndView("LoginForm");
		
		return model1;
	}
	
	/*@RequestMapping(value="/test", method = RequestMethod.GET )
	public ModelAndView test() {

		ModelAndView model1 = new ModelAndView("test");
		
		return model1;
	}*/
	
	@RequestMapping(value="/login", method = RequestMethod.POST )
	public ModelAndView loginForm(@Valid @ModelAttribute("userLogin") UserLogin userLogin, BindingResult result) {

		 if (result.hasErrors()) {
			 	
				ModelAndView model1 = new ModelAndView("LoginForm");
				return model1;
		 }
		 db = new dbOperations();
		 if(userLogin.getUserType().equals("company"))
		 {
			 List<Map<String, String>> list = db.checkLogin(userLogin,"companyId");
			 String c_id = "";
			 String username = "";
			 if(list.size()>0){
				 for(Map<String, String> map : list){
					c_id = map.get("companyId");
					username = map.get("username");
				}
				
		        List list3 = db.getSource("c_id",c_id);
				//List list4 = db.getDestination("c_id",c_id);
				List list5 = db.getTruckType("c_id",c_id);
		    	
				ModelAndView model1 = new ModelAndView("company");
				model1.addObject("companyid", c_id);
				model1.addObject("username", username);
		    	if(list3.size()>0){
	    			model1.addObject("lists3", list3);
	    		}
	    		/*if(list4.size()>0){
	    			model1.addObject("lists4", list4);
	    		}*/
	    		if(list5.size()>0){
	    			model1.addObject("lists5", list5);
	    		}
		    	return model1;
		    }
		    else {
		    	ModelAndView model1 = new ModelAndView("LoginForm");
		    	model1.addObject("message", "Invalid Username or Password");
		    	return model1;
		    }
		 }
		 else
		 {
			 List list = db.checkLogin(userLogin,"transporterId");
			 List list3 = db.getSource("t_id","0");
			 List list4 = db.getDestination("t_id","0");
			 List list5 = db.getTruckType("t_id","0");
			 Map params = new HashMap<String, String>();
			 if(list.size()>0){
		        params = (Map) list.get(0);
		    	ModelAndView model1 = new ModelAndView("transporter", params);
		    	if(list3.size()>0){
	    			model1.addObject("lists3", list3);
	    		}
	    		if(list4.size()>0){
	    			model1.addObject("lists4", list4);
	    		}
	    		if(list5.size()>0){
	    			model1.addObject("lists5", list5);
	    		}
		    	return model1;
		    }
		    else {
		    	params.put("message", "Invalid Username or Password");
		    	ModelAndView model1 = new ModelAndView("LoginForm", params);
		    	return model1;
		    } 
		 }
		 
	}

	@ModelAttribute
    public void addingCommonObjects(Model model1) {
		
		model1.addAttribute("headerMessage", "Transport Services");
	}

	@RequestMapping(value="/signup", method = RequestMethod.GET)
	public ModelAndView signUpForm() {

		ModelAndView model1 = new ModelAndView("SignUpForm");
		return model1;
	}
	
	@RequestMapping(value="/signupSuccess", method = RequestMethod.POST)
	public ModelAndView submitAdmissionForm(@Valid @ModelAttribute("userSignUp") UserSignUp userSignUp, BindingResult result) {

		 if (result.hasErrors()) {
			 	
				ModelAndView model1 = new ModelAndView("SignUpForm");
				return model1;
		 }
		db = new dbOperations();
		int flag = 0;
		List list1 = db.checkUserName(userSignUp);
		ModelAndView model1 = new ModelAndView("SignUpForm");
	    if(list1.size()>0){
	    		model1.addObject("msg1", "Username already exists !");
	    		flag=1;
	    	}
	    List list2 = db.checkEmail(userSignUp);
	    if(list2.size()>0){
	    		model1.addObject("msg2", "Email already exists !");
	    		flag=1;
	    	}
	    List list3 = db.checkContact(userSignUp);
	    if(list3.size()>0){
	    		model1.addObject("msg3", "Contact No. already exists !");
	    		flag=1;
	    	}
	    if(flag==1)
	    	return model1;
	    
	    	boolean success = db.registerUser(userSignUp);
			
			ModelAndView model2= new ModelAndView("SignUpSuccess");
			
			if(success)
				model2.addObject("msg", "Registered successfully !");
			else
				model2.addObject("msg", " There is some problem in registeration ! ");
			
			return model2;
	    
		
	}
	
	@RequestMapping(value="/registerTruck", method = RequestMethod.POST)
	public @ResponseBody String registerTruck(HttpServletRequest request) {
		
		String destinationName = request.getParameter("destinationName");
		String sourceName = request.getParameter("sourceName");
		String trucktype = request.getParameter("trucktype");
		String reg = request.getParameter("reg");
		String driver_name = request.getParameter("driver_name");
		String driver_mobile = request.getParameter("driver_mobile");
		String pan = request.getParameter("pan");
		String userName = request.getParameter("userName");
		db = new dbOperations();
		List list = db.checkTruck(reg);
		if(list.size()>0)
			return "Above Truck is already registered !";
		else
		{
			if(db.registerTruck(destinationName, sourceName, trucktype, reg, driver_name, driver_mobile, pan, userName))
				return "Truck has been registered successfully !";
			else
				return "There is some problem, Please try again !";
		}			
	}
	
	@RequestMapping(value="/bookTruckFunction1", method = RequestMethod.POST)
	public @ResponseBody ModelAndView bookTruck1(HttpServletRequest request) {
		
		String param1 = request.getParameter("param1");
		String param2 = request.getParameter("param2");
		String location = request.getParameter("location");
		String c_id = request.getParameter("c_id");
		db = new dbOperations();
		List list = db.getLocation(param1, param2, location, c_id);	
		ModelAndView model1 = new ModelAndView("sourceDestLocation");
		model1.addObject("lists", list);
		if(param2 == "sourceId")
			param2 = "source";
		else
			param2 = "destination";
		model1.addObject("location", param2);
		return model1;
	}
	
	@RequestMapping(value="/bookTruckFunction2", method = RequestMethod.POST)
	public @ResponseBody ModelAndView bookTruck2(HttpServletRequest request) {
		
		String location = request.getParameter("location");
		String c_id = request.getParameter("c_id");
		db = new dbOperations();
		List list = db.getLocAddress(location, c_id);	
		ModelAndView model1 = new ModelAndView("locationAddress");
		model1.addObject("lists", list);
		return model1;
	}
	
	@RequestMapping(value="/bookTruckFunction3", method = RequestMethod.POST)
	public @ResponseBody String bookTruck3(HttpServletRequest request) {
		
		String location = request.getParameter("city");
		String c_id = request.getParameter("c_id");
		String address = request.getParameter("address");
		db = new dbOperations();
		List list = db.checkLocAddress(address, c_id);
		if(list.size() > 0)
			return "Above location is already present !";
		else{
			if(db.inserLocAddress(location, address, c_id))
				return "Location added successfully, Now chose the location from the list";
			else
				return "There is some problem in adding location, Please try again !";
		}
		
	}
	
	@RequestMapping(value="/bookTruckFunction4", method = RequestMethod.POST)
	public @ResponseBody ModelAndView bookTruck4(HttpServletRequest request) {
		
		String destination = request.getParameter("dest");
		String source = request.getParameter("source");
		String c_id = request.getParameter("c_id");
		db = new dbOperations();
		List list = db.bookTruckType(c_id, source, destination);	
		ModelAndView model1 = new ModelAndView("truckType");
		model1.addObject("lists", list);
		return model1;
	}
	
	@RequestMapping(value="/bookTruck", method = RequestMethod.POST)
	public @ResponseBody String bookTruck(HttpServletRequest request) {
		
		String destination = request.getParameter("destination");
		String source = request.getParameter("source");
		String trucktype = request.getParameter("trucktype");
		String destloc = request.getParameter("destloc");
		String srcloc = request.getParameter("srcloc");
		String c_id = request.getParameter("c_id");
		String date = request.getParameter("bookingDate");
		db = new dbOperations();
		List list = db.checkBooking(source, srcloc, destination, destloc, trucktype, date, c_id);
		if(list.size()>0)
			return "1";
		else
		{
			if(db.bookTruck(source, srcloc, destination, destloc, trucktype, date, c_id))
				return "Truck has been booked successfully !";
			else
				return "There is some problem, Please try again !";
		}			
	}
	
	@RequestMapping(value="/forceBookTruck", method = RequestMethod.POST)
	public @ResponseBody String forcebookTruck(HttpServletRequest request) {
		
		String destination = request.getParameter("destination");
		String source = request.getParameter("source");
		String trucktype = request.getParameter("trucktype");
		String destloc = request.getParameter("destloc");
		String srcloc = request.getParameter("srcloc");
		String c_id = request.getParameter("c_id");
		String date = request.getParameter("bookingDate");
		db = new dbOperations();
		
		if(db.bookTruck(source, srcloc, destination, destloc, trucktype, date, c_id))
			return "Truck has been booked successfully !";
		else
			return "There is some problem, Please try again !";		
	}
	
	@RequestMapping(value="/ViewBooking", method = RequestMethod.POST)
	public @ResponseBody ModelAndView ViewBooking(HttpServletRequest request) {
		
		String c_id = request.getParameter("c_id");
		ModelAndView model1 = new ModelAndView("ViewBooking");
		db = new dbOperations();
		List list = db.viewBooking(c_id);
		if(list.size()>0)
			model1.addObject("list", list);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
	}
	
	@RequestMapping(value="/ViewCompany", method = RequestMethod.POST)
	public @ResponseBody ModelAndView ViewCompany(HttpServletRequest request) {
		
		String c_id = request.getParameter("c_id");
		ModelAndView model1 = new ModelAndView("ViewCompany");
		db = new dbOperations();
		List list = db.viewCompany(c_id);
		if(list.size()>0)
			model1.addObject("list", list);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
	}
	
	@RequestMapping(value="/ViewData", method = RequestMethod.POST)
//	public @ResponseBody ArrayList<Map<String,String>> ViewData(HttpServletRequest request) {
// Added some shit comment
	public @ResponseBody ModelAndView ViewData(HttpServletRequest request) {

		String username = request.getParameter("username");
		ModelAndView model1 = new ModelAndView("ViewTruck");
		db = new dbOperations();
		ArrayList<Map<String, String>> list = (ArrayList<Map<String, String>>) db.displayTruck(username);
		if(list.size()>0)
			model1.addObject("lists", list);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
//		return list;
	}
	
}

