package com.rna.db;

import java.util.HashMap;
import java.sql.* ;
import java.util.ArrayList;
import java.util.Map;
import com.rna.variables.variables;
import com.rna.login.UserLogin;
import com.rna.register.*;

public class dbOperations{
	
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static String DB_URL = "jdbc:mysql://localhost:3306/rna";

	 //  Database credentials
	static String USER = "root";
	static String PASS = "";
	private String sql;
	private Connection conn = null;
	private Statement stmt = null;
	private String tableName;
	private Address address;
	private String userType;
	private variables var = new variables();
	
	//Fetching the Common variables 
	public void commonCheck(UserSignUp user){
		
		var.userName = user.getUsername();
		var.password = user.getPassword();
		userType = user.getUserType();
		var.contact = user.getMobile();
		var.name = user.getName();
		var.email = user.getEmail();
		tableName = "transporter";
		
		if(userType.equals("company"))
			tableName = "company";
		
	}
	
	//Register user (both transporter and company)
	public boolean registerUser(UserSignUp user, String address) {

		commonCheck(user);
		
		sql = "INSERT INTO "+tableName+" (name, mobile, username, email, password, address) VALUES ('" + var.name + "','"+var.contact+"','"+ var.userName+"','"+ var.email+"','"+ var.password+"','"+address+"')";
		System.out.println(sql);
		return executeInsert(sql);
				
	}
	
	// verifies that email is unique 
	public ArrayList<Map<String, String>> checkEmail(UserSignUp user){
		commonCheck(user);
		sql =  "SELECT email FROM "+tableName+" WHERE email = '" + var.email + "';";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// verifies that username is unique 
	public ArrayList<Map<String, String>> checkUserName(UserSignUp user){
		commonCheck(user);
		sql =  "SELECT username FROM "+tableName+" WHERE username = '" + var.userName + "';";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// verifies that contact is unique 
	public ArrayList<Map<String, String>> checkContact(UserSignUp user){
		commonCheck(user);
		sql =  "SELECT mobile FROM "+tableName+" WHERE mobile = '" + var.contact + "';";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// verifies that registration number is unique
	public ArrayList<Map<String, String>> checkTruck(String registrationNumber){
		sql =  "SELECT distinct registrationNumber FROM truck WHERE registrationNumber = '"+registrationNumber+"'";
		return executeSelect(sql);
	}
	
	// verifies that location address is unique 
	public ArrayList<Map<String, String>> checkLocAddress(String address, String compamyId){
		sql =  "SELECT address FROM locationaddress WHERE address = '"+address+"' and companyId = '"+compamyId+"' ";
		return executeSelect(sql);
	}
	
	// verifies that contract is not added already for the given route for a company 
	public ArrayList<Map<String, String>> checkContract(String companyId, String destinationName, String sourceName, String trucktype){

		String[] truckTypeArray = trucktype.split(",");
		String truckTypeString = "";
		for(int i=0; i<truckTypeArray.length; i++)
		{
			truckTypeString = "'"+truckTypeArray[i]+"',";
		}
		truckTypeString = truckTypeString.substring(0, truckTypeString.length()-1);
		
		sql = "select locationId from location where locationName = '" +sourceName+ "' ";
		ArrayList<Map<String, String>> list = executeSelect(sql);
		String sourceId = "";
		for(Map<String, String> map : list){
			sourceId = map.get("locationId");
		}
		
		sql = "select locationId from location where locationName = '" +destinationName+ "' ";
		list = executeSelect(sql);
		String destinationId = "";
		for(Map<String, String> map : list){
			destinationId = map.get("locationId");
		}
		
		sql =  "SELECT * FROM contract WHERE companyId = '" +companyId+ "' and truckType IN ("+truckTypeString+") and routeId = (select routeId from route where sourceId = '" +sourceId+ "' and destinationId = '"+destinationId+"' )";
		return executeSelect(sql);
	}
	
	// verifies that booking is not added already for the given route for a company on the same date
	public ArrayList<Map<String, String>> checkBooking(String sourceName, String sourceAddress, String destinationName, String destinationAddress, String trucktype, String bookingDate, String compamyId){
		
		sql = "select locationId from location where locationName = '" +sourceName+ "' ";
		ArrayList<Map<String, String>> list = executeSelect(sql);
		String sourceId = "";
		for(Map<String, String> map : list){
			sourceId = map.get("locationId");
		}
		
		sql = "select locationId from location where locationName = '" +destinationName+ "' ";
		list = executeSelect(sql);
		String destinationId = "";
		for(Map<String, String> map : list){
			destinationId = map.get("locationId");
		}
		
		sql =  "SELECT * FROM booking WHERE companyId = '" +compamyId+ "' and routeId = (select routeId from route where sourceId = '" +sourceId+ "' and destinationId = '"+destinationId+"' ) and dateOfBooking = '"+bookingDate+"' and sourceLocation = '"+sourceAddress+"' and destinationLocation = '"+destinationAddress+"' ";
		return executeSelect(sql);
	}
	
	// returns routeID for the given source and destination 
	public ArrayList<Map<String, String>> getRoute(String sourceName, String destinationName){
		sql = "select routeId from route where sourceId = (select locationId from location where locationName = '" +sourceName+ "') and destinationId = (select locationId from location where locationName = '" +destinationName+ "') ";
		return executeSelect(sql);
	}
	
	// returns all companyID and name 
	public ArrayList<Map<String, String>> getCompanyList(){
		sql =  "SELECT companyId, name FROM company";
		return executeSelect(sql);
	}
	
	// returns source list according to the userType
	public ArrayList<Map<String, String>> getSource(String usertype, String userid){
		if(usertype.equals("transporter"))
			sql =  "SELECT distinct locationId, locationName FROM location where locationId IN (select distinct sourceId from route)";
		else
			sql = "select locationId, locationName FROM location where locationId IN (select sourceId from route where routeId IN (select routeId from contract where companyId = '"+userid+"'))";
		System.out.println(sql);	
		return executeSelect(sql);
	}
	
	// returns destination list according to the userType
	public ArrayList<Map<String, String>> getDestination(String usertype, String userid){
		if(usertype.equals("transporter"))
			sql =  "SELECT distinct locationId, locationName FROM location where locationId IN (select distinct destinationId from route)";
		else
			sql = "select locationId, locationName from location where locationId IN (select destinationId from route where routeId = (select routeId from contract where companyId = '"+userid+"'))";
		
		return executeSelect(sql);
	}
	
	// returns truck-type according to the userType
	public ArrayList<Map<String, String>> getTruckType(String usertype, String userid){
		if(usertype.equals("transporter"))
			sql =  "SELECT distinct truckType FROM trucktype";
		else
			sql = "select truckType from contract where companyId = '"+userid+"'";
		return executeSelect(sql);
	}
	
	// returns destination corresponding to a source from route table
	public ArrayList<Map<String, String>> getLocation(String sourceId, String destinationId, String sourceName, String compamyId){
		
		sql = "select locationName as place from location where locationId IN (select "+destinationId+" from route where routeId IN (select routeId from contract where companyId = '"+compamyId+"') and "+sourceId+" IN (select "+sourceId+" from location where locationName = '"+sourceName+"'))  ";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// returns list of addresses at a given city/location
	public ArrayList<Map<String, String>> getLocAddress(String location, String compamyId){
		sql = "select id, address from locationaddress where locationId = (select locationId from location where locationName = '"+location+"') and companyId='"+compamyId+"' ";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// returns details for Admin from tables
	public ArrayList<Map<String, String>> displayForAdmin(String tablename){
		
		sql =  "SELECT name, mobile, address FROM "+ tablename;
		return executeSelect(sql);
	}
	
	// returns truck type for given company and route from contract table
	public ArrayList<Map<String, String>> bookTruckType(String compamyId, String sourceName, String destinationName){
		sql =  "SELECT truckType from contract where companyId = '"+compamyId+"' and routeId = (select routeId from route where sourceId IN (select locationId from location where locationName = '"+sourceName+"' ) and destinationId IN (select locationId from location where locationName = '"+destinationName+"' )) ";
		return executeSelect(sql);
	}
	
	// For booking a truck  
	public boolean bookTruck(String sourceName, String sourceAddress, String destinationName, String destinationAddress, String trucktype, String bookingDate, String compamyId){
		
		ArrayList<Map<String, String>> list = getRoute(sourceName, destinationName);
		String routeId = "";
		for(Map<String, String> map : list){
			System.out.println(routeId = map.get("routeId"));
		}
		sql = "insert into booking (dateOfBooking, companyId, routeId, sourceLocation, destinationLocation, truckType) values ('"+bookingDate+"','"+compamyId+"','"+routeId+"','"+sourceAddress+"','"+destinationAddress+"','"+trucktype+"')";
		return executeInsert(sql);
	}
	
	// To add a contract entry 
	public boolean addDestination(String companyID, String destinationName, String sourceName, String trucktype){
		
		sql = "select locationName from location where locationName = '"+sourceName+"' ";
		ArrayList<Map<String, String>> list = executeSelect(sql);
		if(list.size() <= 0)
		{
			sql = "insert into location (locationName) values ('"+sourceName+"')";
			executeInsert(sql);
		}
		
		sql = "select locationName from location where locationName = '"+destinationName+"' ";
		list = executeSelect(sql);
		if(list.size() <= 0)
		{
			sql = "insert into location (locationName) values ('"+destinationName+"')";
			executeInsert(sql);
		}
		
		list = getRoute(sourceName, destinationName);
		if(list.size() <= 0)
		{
			sql = "select locationId from location where locationName = '"+destinationName+"' ";
			list = executeSelect(sql);
			
			String destinationId = "";
			for(Map<String, String> map : list){
				destinationId = map.get("locationId");
			}
			
			sql = "select locationId from location where locationName = '"+sourceName+"' ";
			list = executeSelect(sql);
			
			String sourceId = "";
			for(Map<String, String> map : list){
				sourceId = map.get("locationId");
			}
			
			sql = "insert into route (sourceId, destinationId) values ('"+sourceId+"','"+destinationId+"')";
			executeInsert(sql);
		}
		
		list = getRoute(sourceName, destinationName);
		String routeId = "";
		for(Map<String, String> map : list){
			routeId = map.get("routeId");
		}
		
		String[] truckArray = trucktype.split(",");
		int i;
		for( i=0; i<truckArray.length; i++)
		{
			sql = "insert into contract (routeId, truckType, companyId) values ('"+routeId+"','"+truckArray[i]+"','"+companyID+"')";
			if(!executeInsert(sql))
				break;
		}
		if(i == truckArray.length)
			return true;
		else
			return false;
	}
	
	// adds a truck for a transporter 
	public boolean registerTruck(String destinationName, String sourceName, String trucktype, String registerationNumber, String driver_name, String driver_mobile, String panNumber, String userName){
		
		sql = "select transporterId from transporter where username='"+userName+"'";
		ArrayList<Map<String, String>> list = executeSelect(sql);
		String transporterId = "";
		for(Map<String, String> map : list){
			transporterId = map.get("transporterId");
		}
		
		sql = "select * from truckdriver where name = '" +driver_name+ "' and mobile = '"+driver_mobile+"' ";
		list = executeSelect(sql);
		if(list.size() <= 0)
		{
			sql = "insert into truckdriver (transporterId, name, mobile) values ('"+transporterId+"','"+driver_name+"','"+driver_mobile+"')";
			executeInsert(sql);
		}
		
		list = getRoute(sourceName, destinationName); 
		String routeId = "";
		for(Map<String, String> map : list){
			routeId = map.get("routeId");
		}
		
		sql = "select truckDriverId from truckdriver where name = '" +driver_name+ "' and mobile = '"+driver_mobile+"' ";
		list = executeSelect(sql);
		String truckDriverId = "";
		for(Map<String, String> map : list){
			truckDriverId = map.get("truckDriverId");
		}
		
		sql = "insert into truck (truckDriverId, routeId, registrationNumber, truckType, panNumber, transporterId) values ('"+truckDriverId+"','"+routeId+"','"+registerationNumber+"','"+trucktype+"','"+panNumber+"','"+transporterId+"')";
		if(executeInsert(sql))
			return true;
		else
			return false;
	}
	
	// Add an address for corresponding location and company 
	public boolean inserLocAddress(String location, String address, String compamyId){
		sql = "select locationId as id from location where locationName = '" +location+ "' ";
		ArrayList<Map<String, String>> list = executeSelect(sql);
		String id = "";
		for(Map<String, String> map : list){
			id = map.get("id");
		}
		sql = "insert into locationaddress (locationId, companyId, address) values ('"+id+"','"+compamyId+"','"+address+"')";
		return executeInsert(sql);
	}
	
	//  Verifies the user credentials from database
	public ArrayList<Map<String, String>> checkLogin(String userName, String password, String userType, String userid){
		
		tableName = "transporter";
		
		if(userType.equals("company"))
			tableName = "company";
		
		sql =  "SELECT username, "+userid+" FROM "+tableName+" WHERE username = '" + userName + "' AND password = '" + password + "';";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// Verifies Admin credentials
	public ArrayList<Map<String, String>> checkadminLogin(String username, String password){
		
		sql =  "SELECT username FROM admin WHERE username = '" + username + "' AND password = '" + password + "';";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// Display credentials of a truck 
	public ArrayList<Map<String, String>> displayTruck(String username){
		
		sql =  "SELECT truckdriver.name, truck.truckId, truck.registrationNumber, truck.truckType FROM truckdriver, truck WHERE truckdriver.truckDriverId IN (select truckDriverId from truckdriver where transporterId = (select transporterId from transporter where username='" + username + "')) and truckdriver.truckDriverId=truck.truckDriverId ";
		return executeSelect(sql);
	}
	
	//View all bookings sorted by date   
	public ArrayList<Map<String, String>> viewBooking(String compamyId){
		
		sql =  "select * from (select booking.companyId, booking.dateOfBooking, booking.truckType, booking.routeId, route.sourceId, route.destinationId from booking join route on booking.routeId=route.routeId) as table1 where companyId = '"+compamyId+"' order by dateOfBooking DESC ";
		return executeSelect(sql);
	}
	
	// View credentials of a company 
	public ArrayList<Map<String, String>> viewCompany(String compamyId){
		
		sql =  "select company.companyId, company.name, company.mobile, company.email, company.address, route.routeId, route.sourceId, route.destinationId from company, route where company.companyId = '"+compamyId+"' and route.routeId IN (select routeId from contract where companyId = '"+compamyId+"') ";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	// executing the select queries 
	public ArrayList<Map<String,String>> executeSelect(String sql){
	   		   	   
		   try{
			  //STEP 2: Register JDBC driver
			  Class.forName("com.mysql.jdbc.Driver");	
			  //STEP 3: Open a connection
			  System.out.println("Connecting to database...");
			  conn = DriverManager.getConnection(DB_URL,USER,PASS);
			  //STEP 4: Execute a query
			  System.out.println("Creating statement...");
			  stmt = conn.createStatement();
		      ResultSet rs = stmt.executeQuery(sql);
		      ResultSetMetaData md = rs.getMetaData();
		      int columns = md.getColumnCount();
		      System.out.println(columns+"hi");
		      ArrayList<Map<String,String>> list = new ArrayList<Map<String,String>>();
		      while(rs.next()){
			      Map map = new HashMap<String, String>();
		    	  for(int i=1; i<=columns; i++ ){
		    		  map.put(md.getColumnLabel(i), rs.getString(i));
		    	  }
		    	  list.add(map);
		      }
		      stmt.close();
		      conn.close();
		      rs.close();
	    	  return list;
		   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			      return null;
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			      return null;
			   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }
	}
	
	// Executing the insert queries 
	public boolean executeInsert(String sql){
		
		try{	
			//STEP 2: Register JDBC driver
			  Class.forName("com.mysql.jdbc.Driver");	
			  //STEP 3: Open a connection
			  System.out.println("Connecting to database...");
			  conn = DriverManager.getConnection(DB_URL,USER,PASS);
			  //STEP 4: Execute a query
			  System.out.println("Creating statement...");
			  stmt = conn.createStatement();
			  
		      int out = stmt.executeUpdate(sql);
		      stmt.close();
		      conn.close();
		      //System.out.println(out);
		      
		      if(out > 0)
		    	  return true;
		      
		      else 
		     	  return false;
		   }catch(SQLException se){
			      //Handle errors for JDBC
			      se.printStackTrace();
			      return false;
			   }catch(Exception e){
			      //Handle errors for Class.forName
			      e.printStackTrace();
			      return false;
			   }finally{
		      //finally block used to close resources
		      try{
		         if(stmt!=null)
		            stmt.close();
		      }
		      catch(SQLException se2){
		      }// nothing we can do
		      try{
		         if(conn!=null)
		            conn.close();
		      }
		      catch(SQLException se){
		         se.printStackTrace();
		      }//end finally try
		   }
	}
}