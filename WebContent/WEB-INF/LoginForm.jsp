<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
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
		<h3>Login Form</h3>
		<br>
		<h3>${message}</h3>
		<br>
		<div class="submain">
		<form:form action="/RNA/login" commandName="userLogin" method="post">
		
			<table>
				<tr><td>Username : </td><td><input type="text" name="username" /></td><td><form:errors path="username" class="error"/></td></tr>
				<tr><td>Password : </td><td><input type="password" name="password" /></td><td><form:errors path="password" class="error"/></td></tr>
				<tr><td>User Type : </td><td>Company : <input type="radio" checked name="userType" value="company" /> Transporter : <input type="radio" name="userType" value="transporter" /></td><td></td></tr>
				<tr><td><a href="/RNA/signup">Not a member Yet, Sign Up !</a></td><td><input type="submit" value="Login" /></td><td></td></tr>
			</table>	
		</form:form>
		</div>
 	</div>
</body>
</html>

