<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Admin Login</title>
	
	<spring:url value="/resources/js/jquery.js" var="jqueryJS" />
	<spring:url value="/resources/js/jquery-new.js" var="jquerynewJS" />
	<spring:url value="/resources/js/jquery-1.10.2.js" var="jquery2JS" />
	<spring:url value="/resources/js/function.js" var="functionJS" />
	
	<script src="${jqueryJS}"></script>
	<script src="${jquerynewJS}"></script>
	<script src = "${jquery2JS}" ></script>
	<script src = "${functionJS}" ></script>
	
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
		
		.error_message { 
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
		<form:form action="/RNA/admin/admin" id="adminLoginForm" method="post" >
		
			<table>
				<tr><td>Username :  <input type="text" name="username"  /></td><td><span class="error"/></span></td></tr>       
				<tr><td>Password :  <input type="password" name="password" ></td><td><span class="error"/></span></td></tr>      
				<tr><td><br><input type="submit" name="submit2" value="Login" /></td></tr>			
			</table>
			<br>
			<span class="error_message" ></span>	
		</form:form>
		</div>
 	</div>
</body>
</html>