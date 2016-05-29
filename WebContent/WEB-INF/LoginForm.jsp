<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<spring:url value="/resources/js/jquery.js" var="jqueryJS" />
	<spring:url value="/resources/js/jquery-new.js" var="jquerynewJS" />
	<spring:url value="/resources/js/jquery-1.10.2.js" var="jquery2JS" />
	<spring:url value="/resources/js/function.js" var="functionJS" />
	
	<script src="${jqueryJS}"></script>
	<script src="${jquerynewJS}"></script>
	<script src = "${jquery2JS}" ></script>
	<script src = "${functionJS}" ></script>
	
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
		
		.error_message{ 
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
		<div class="submain">
		<form:form id="adminLoginForm" action = "/RNA/USER" method="POST" commandName="userLogin" >
		
			<table >
				<tr><td>Username : </td><td><input type="text" name="username" /></td><td><span class="error"/></span></td></tr>
				<tr><td>Password : </td><td><input type="password" name="password" /></td><td><span class="error"/></span></td></tr>
				<tr><td>User Type : </td><td>Company : <input type="radio" checked name="userType" value="company" /> Transporter : <input type="radio" name="userType" value="transporter" /></td><td></td></tr>
				<tr><td><a href="/RNA/signup">Not a member Yet, Sign Up !</a></td><td><input type="submit" value="Login" id="login" /></td><td></td></tr>
			</table>
			<br>
			<span class="error_message" ></span>	
		</form:form>
		</div>
 	</div>
</body>
</html>

