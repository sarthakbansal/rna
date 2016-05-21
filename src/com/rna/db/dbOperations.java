package com.rna.db;

import java.util.HashMap;
import java.sql.* ;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.rna.login.UserLogin;
import com.rna.register.*;

public class dbOperations{
	
	static String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static String DB_URL = "jdbc:mysql://localhost:3306/rna";

	 //  Database credentials
	static String USER = "root";
	static String PASS = "";
	private String sql;
	private Address address;
	private String addr;
	private String name;
	private Long mobile;
	private String username;
	private String email;
	private String password;
	private Connection conn = null;
	private Statement stmt = null;
	private String tableName;
	private String userType;
	
	public void commonCheck(UserSignUp user){
		
		username = user.getUsername();
		password = user.getPassword();
		userType = user.getUserType();
		mobile = user.getMobile();
		name = user.getName();
		email = user.getEmail();
		tableName = "transporter";
		
		if(userType.equals("company"))
			tableName = "company";
		
	}
	
	public boolean registerUser(UserSignUp user) {

		address = user.getUserAddress();
		
		addr = address.getStreet() + " Street " + address.getCity() + " " + address.getPincode() + " " + address.getCountry();
		
		commonCheck(user);
		
		sql = "INSERT INTO "+tableName+" (name, mobile, username, email, password, address) VALUES ('" + name + "','"+ mobile+"','"+ username+"','"+ email+"','"+ password+"','"+addr+"')";

		return executeInsert(sql);
				
	}
	
	public List checkEmail(UserSignUp user){
		commonCheck(user);
		sql =  "SELECT email FROM "+tableName+" WHERE email = '" + email + "';";
		return executeSelect(sql);
	}
	
	public List checkUserName(UserSignUp user){
		commonCheck(user);
		sql =  "SELECT username FROM "+tableName+" WHERE username = '" + username + "';";
		return executeSelect(sql);
	}
	
	public List checkContact(UserSignUp user){
		commonCheck(user);
		sql =  "SELECT mobile FROM "+tableName+" WHERE mobile = '" + mobile + "';";
		return executeSelect(sql);
	}
	
	public List checkTruck(String reg){
		sql =  "SELECT distinct registrationNumber FROM truck WHERE registrationNumber = '"+reg+"'";
		return executeSelect(sql);
	}
	
	public List checkLocAddress(String address, String c_id){
		sql =  "SELECT address FROM locationaddress WHERE address = '"+address+"' and companyId = '"+c_id+"' ";
		return executeSelect(sql);
	}
	
	public List checkContract(String companyID, String destinationName, String sourceName, String trucktype){

		String[] truckArray = trucktype.split(",");
		String str = "";
		for(int i=0; i<truckArray.length; i++)
		{
			str = "'"+truckArray[i]+"',";
		}
		str = str.substring(0, str.length()-1);
		
		sql = "select locationId from location where locationName = '" +sourceName+ "' ";
		List<Map<String, String>> list = executeSelect(sql);
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
		
		sql =  "SELECT * FROM contract WHERE companyId = '" +companyID+ "' and truckType IN ("+str+") and routeId = (select routeId from route where sourceId = '" +sourceId+ "' and destinationId = '"+destinationId+"' )";
		return executeSelect(sql);
	}
	
	public List checkBooking(String source, String srcloc, String destination, String destloc, String trucktype, String date, String c_id){
		
		sql = "select locationId from location where locationName = '" +source+ "' ";
		List<Map<String, String>> list = executeSelect(sql);
		String sourceId = "";
		for(Map<String, String> map : list){
			sourceId = map.get("locationId");
		}
		
		sql = "select locationId from location where locationName = '" +destination+ "' ";
		list = executeSelect(sql);
		String destinationId = "";
		for(Map<String, String> map : list){
			destinationId = map.get("locationId");
		}
		
		sql =  "SELECT * FROM booking WHERE companyId = '" +c_id+ "' and routeId = (select routeId from route where sourceId = '" +sourceId+ "' and destinationId = '"+destinationId+"' ) and dateOfBooking = '"+date+"' and sourceLocation = '"+srcloc+"' and destinationLocation = '"+destloc+"' ";
		return executeSelect(sql);
	}
	
	public List getRoute(String source, String destination){
		sql = "select routeId from route where sourceId = (select locationId from location where locationName = '" +source+ "') and destinationId = (select locationId from location where locationName = '" +destination+ "') ";
		return executeSelect(sql);
	}
	
	public List getCompanyList(){
		sql =  "SELECT companyId, name FROM company";
		return executeSelect(sql);
	}
	public List getSource(String usertype, String userid){
		if(usertype.equals("t_id"))
			sql =  "SELECT distinct locationId, locationName FROM location where locationId IN (select distinct sourceId from route)";
		else
			sql = "select locationId, locationName FROM location where locationId IN (select sourceId from route where routeId IN (select routeId from contract where companyId = '"+userid+"'))";
			
		return executeSelect(sql);
	}
	public List getDestination(String usertype, String userid){
		if(usertype.equals("t_id"))
			sql =  "SELECT distinct locationId, locationName FROM location where locationId IN (select distinct destinationId from route)";
		else
			sql = "select locationId, locationName from location where locationId IN (select destinationId from route where routeId = (select routeId from contract where companyId = '"+userid+"'))";
		
		return executeSelect(sql);
	}
	public List getTruckType(String usertype, String userid){
		if(usertype.equals("t_id"))
			sql =  "SELECT distinct truckType FROM trucktype";
		else
			sql = "select truckType from contract where companyId = '"+userid+"'";
		return executeSelect(sql);
	}
	
	public List getLocation(String param1, String param2, String location, String c_id){
		
		sql = "select locationName as place from location where locationId IN (select "+param2+" from route where routeId IN (select routeId from contract where companyId = '"+c_id+"') and "+param1+" IN (select "+param1+" from location where locationName = '"+location+"'))  ";
		return executeSelect(sql);
	}
	
	public List getLocAddress(String location, String c_id){
		sql = "select id, address from locationaddress where locationId = (select locationId from location where locationName = '"+location+"') and companyId='"+c_id+"' ";
		return executeSelect(sql);
	}
	
	public List displayForAdmin(String tablename){
		
		sql =  "SELECT name, mobile, address FROM "+ tablename;
		return executeSelect(sql);
	}
	
	public List bookTruckType(String c_id, String source, String destination){
		sql =  "SELECT truckType from contract where companyId = '"+c_id+"' and routeId = (select routeId from route where sourceId IN (select locationId from location where locationName = '"+source+"' ) and destinationId IN (select locationId from location where locationName = '"+destination+"' )) ";
		return executeSelect(sql);
	}
	
	public boolean bookTruck(String source, String srcloc, String destination, String destloc, String trucktype, String date, String c_id){
		
		List<Map<String, String>> list = getRoute(source, destination);
		String routeId = "";
		for(Map<String, String> map : list){
			System.out.println(routeId = map.get("routeId"));
		}
		sql = "insert into booking (dateOfBooking, companyId, routeId, sourceLocation, destinationLocation, truckType) values ('"+date+"','"+c_id+"','"+routeId+"','"+srcloc+"','"+destloc+"','"+trucktype+"')";
		return executeInsert(sql);
	}
	
	public boolean addDestination(String companyID, String destinationName, String sourceName, String trucktype){
		
		sql = "select locationName from location where locationName = '"+sourceName+"' ";
		List<Map<String, String>> list = executeSelect(sql);
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
	
	public boolean registerTruck(String destinationName, String sourceName, String trucktype, String reg, String driver_name, String driver_mobile, String pan, String userName){
		
		sql = "select transporterId from transporter where username='"+userName+"'";
		List<Map<String, String>> list = executeSelect(sql);
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
		
		sql = "insert into truck (truckDriverId, routeId, registrationNumber, truckType, panNumber, transporterId) values ('"+truckDriverId+"','"+routeId+"','"+reg+"','"+trucktype+"','"+pan+"','"+transporterId+"')";
		if(executeInsert(sql))
			return true;
		else
			return false;
	}
	
	public boolean inserLocAddress(String location, String address, String c_id){
		sql = "select locationId as id from location where locationName = '" +location+ "' ";
		List<Map<String, String>> list = executeSelect(sql);
		String id = "";
		for(Map<String, String> map : list){
			id = map.get("id");
		}
		sql = "insert into locationaddress (locationId, companyId, address) values ('"+id+"','"+c_id+"','"+address+"')";
		return executeInsert(sql);
	}
	
	public List checkLogin(UserLogin user, String userid){
		username = user.getUsername();
		password = user.getPassword();
		userType = user.getUserType();
		tableName = "transporter";
		
		if(userType.equals("company"))
			tableName = "company";
		
		sql =  "SELECT username, "+userid+" FROM "+tableName+" WHERE username = '" + username + "' AND password = '" + password + "';";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
	public List checkadminLogin(String username, String password){
		
		sql =  "SELECT username FROM admin WHERE username = '" + username + "' AND password = '" + password + "';";
		return executeSelect(sql);
	}
	
	public List displayTruck(String username){
		
		sql =  "SELECT truckdriver.name, truck.truckId, truck.registrationNumber, truck.truckType FROM truckdriver, truck WHERE truckdriver.truckDriverId IN (select truckDriverId from truckdriver where transporterId = (select transporterId from transporter where username='" + username + "')) and truckdriver.truckDriverId=truck.truckDriverId ";
		return executeSelect(sql);
	}
	
	public List viewBooking(String c_id){
		
		sql =  "select * from (select booking.companyId, booking.dateOfBooking, booking.truckType, booking.routeId, route.sourceId, route.destinationId from booking join route on booking.routeId=route.routeId) as table1 where companyId = '"+c_id+"' order by dateOfBooking DESC ";
		return executeSelect(sql);
	}
	
	public List viewCompany(String c_id){
		
		sql =  "select company.companyId, company.name, company.mobile, company.email, company.address, route.routeId, route.sourceId, route.destinationId from company, route where company.companyId = '"+c_id+"' and route.routeId IN (select routeId from contract where companyId = '"+c_id+"') ";
		System.out.println(sql);
		return executeSelect(sql);
	}
	
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
		      //System.out.println(columns);
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