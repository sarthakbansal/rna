<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin Login</title>
<style>
	.main{
		text-align: center;
	}
	.header { 
		color: blue; 
		font-weight: bold;
		margin-top: 10%;	
	}
	
	table {
   		margin: 0 auto;
   		z-index: 1;
   		width:50%; 
   		height: 50px; 
	}
	
	.error { 
		color: red; font-weight: bold; 
	}
</style>
</head>
<body>
	<div class = "main">
		<h1 class = "header">${headerMessage}</h1>
		<br>
		<h3>Admin Login Form</h3>
		<br>
		<h3 class = "error">${message}</h3>
		<br>
		<div class="submain">
		<form:form action="/RNA/admin/adminLogin"  method="post">
		
			<table>
				<tr><td>Username :  <input type="text" name="username" required />   </td> <td>       
				<tr><td>Password :  <input type="password" name="password" required/>  </td> <td>      
				<tr><td><input type="submit" value="Login" />   </td></tr>			
			</table>	
		</form:form>
		</div>
 	</div>
</body>
</html>