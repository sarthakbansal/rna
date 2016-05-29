package com.rna.signuplogincontroller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.rna.db.dbOperations;
import com.rna.login.UserLogin;
import com.rna.variables.variables;

// admin Controller
@Controller
public class adminController {
	private dbOperations db;
	private variables var;
	
	// Admin login page mapping
	@RequestMapping(value="admin/adminLogin", method = RequestMethod.GET )
	public ModelAndView adminloginForm() {

		ModelAndView model1 = new ModelAndView("admin/adminLogin");
		model1.addObject("headerMessage", "Transport Services");
		return model1;
	}
	
	// Checking for login credentials
	@RequestMapping(value="admin/checkAdminLogin", method = RequestMethod.POST )
	public @ResponseBody ArrayList<Map<String,String>> checkAdminloginForm(HttpServletRequest request) {
		
		db = new dbOperations();
		var = new variables();
	
		var.userName = request.getParameter("username");
		var.password = request.getParameter("password");
		
		
		Map map = new HashMap<String, String>();
		var.loginList = db.checkadminLogin(var.userName, var.password);
		
	    if(var.loginList.size()>0)
			 map.put("message", "success");
		 else
			 map.put("message", "Username or password is incorrect !");
		 
	    var.loginDataList = new ArrayList<Map<String,String>>();
		 var.loginDataList.add(map);
		 System.out.println(var.loginDataList);
		 return var.loginDataList;
	}
	
	//Mapping After  Admin login page submission
	@RequestMapping(value="admin/admin", method = RequestMethod.POST )
	public ModelAndView admin() {

		 db = new dbOperations();
		 var = new variables();
		 
		var.companyList = db.getCompanyList();
			
		var.sourceList = db.getSource("transporter","0");
		var.destinationList = db.getDestination("transporter","0");
		var.truckTypeList = db.getTruckType("transporter","0");
		Map loginData = new HashMap<String, String>();
		    
		ModelAndView model1 = new ModelAndView("admin/admin");
		model1.addObject("headerMessage", "Transport Services");
		if(var.sourceList.size()>0){
		    model1.addObject("sourceList", var.sourceList);
		}
		if(var.destinationList.size()>0){
		   model1.addObject("destinationList", var.destinationList);
		}
		if(var.truckTypeList.size()>0){
			model1.addObject("truckTypeList", var.truckTypeList);
		}
		if(var.companyList.size()>0){
		   model1.addObject("companyList", var.companyList);
		}
		return model1;
	
	}
	
	// For adding a new route tp the contract of a company 
	@RequestMapping(value="admin/addRoute", method = RequestMethod.POST)
	public @ResponseBody String addRoute(HttpServletRequest request) {
		
		
		var = new variables();
		db = new dbOperations();
		var.destinationName = request.getParameter("destinationName");
		var.sourceName = request.getParameter("sourceName");
		var.truckType = request.getParameter("trucktype");
		var.companyID = request.getParameter("companyID");
		
		var.contractList = db.checkContract(var.companyID, var.destinationName, var.sourceName, var.truckType);
		if(var.contractList.size()>0)
			return "Above data is already present !";
		else
		{
			if(db.addDestination(var.companyID, var.destinationName, var.sourceName, var.truckType))
				return "Data has been added successfully !";
			else
				return "There is some problem, Please try again !";
		}			
	}
	
	// For viewing transporter and company details 
	@RequestMapping(value="admin/adminViewData", method = RequestMethod.POST)
	public @ResponseBody ModelAndView adminViewData(HttpServletRequest request) {
		
		String tablename = request.getParameter("tablename");
		ModelAndView model1 = new ModelAndView("admin/adminViewData");
		db = new dbOperations();
		var= new variables();
		
		var.adminDataList = db.displayForAdmin(tablename);
		if(var.adminDataList.size()>0)
			model1.addObject("adminDataList",var.adminDataList);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
	}
}
