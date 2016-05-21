package com.rna.signuplogincontroller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.rna.db.dbOperations;


@Controller
public class adminController {
	private dbOperations db;
	
	@RequestMapping(value="admin/adminLogin", method = RequestMethod.GET )
	public ModelAndView adminloginForm() {

		ModelAndView model1 = new ModelAndView("admin/adminLogin");
		model1.addObject("headerMessage", "Transport Services");
		return model1;
	}
	
	@RequestMapping(value="admin/adminLogin", method = RequestMethod.POST )
	public ModelAndView adminloginForm(@RequestParam("username") String username, @RequestParam("password") String password) {
		
		db = new dbOperations();
		List list = db.checkadminLogin(username, password);
		List list2 = db.getCompanyList();
		List list3 = db.getSource("t_id","0");
		List list4 = db.getDestination("t_id","0");
		List list5 = db.getTruckType("t_id","0");
		Map params = new HashMap<String, String>();
	    if(list.size()>0){
	        	params = (Map) list.get(0);
	    		ModelAndView model1 = new ModelAndView("admin/admin", params);
	    		model1.addObject("headerMessage", "Transport Services");
	    		if(list2.size()>0){
	    			model1.addObject("lists2", list2);
	    		}
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
	    	ModelAndView model1 = new ModelAndView("admin/adminLogin", params);
	    	model1.addObject("headerMessage", "Transport Services");
	    	return model1;
	    }
	}
	@RequestMapping(value="admin/addDestination", method = RequestMethod.POST)
	public @ResponseBody String addDestination(HttpServletRequest request) {
		
		String companyID = request.getParameter("companyID");
		String destinationName = request.getParameter("destinationName");
		String sourceName = request.getParameter("sourceName");
		String trucktype = request.getParameter("trucktype");
		db = new dbOperations();
		List list = db.checkContract(companyID, destinationName, sourceName, trucktype);
		if(list.size()>0)
			return "Above data is already present !";
		else
		{
			if(db.addDestination(companyID, destinationName, sourceName, trucktype))
				return "Data has been added successfully !";
			else
				return "There is some problem, Please try again !";
		}			
	}
	
	@RequestMapping(value="admin/adminViewData", method = RequestMethod.POST)
	public @ResponseBody ModelAndView adminViewData(HttpServletRequest request) {
		
		String tablename = request.getParameter("tablename");
		ModelAndView model1 = new ModelAndView("admin/adminViewData");
		db = new dbOperations();
		List list = db.displayForAdmin(tablename);
		if(list.size()>0)
			model1.addObject("lists", list);
		else
			model1.addObject("err_msg", "Database is empty !");
		
		return model1;
	}
}
