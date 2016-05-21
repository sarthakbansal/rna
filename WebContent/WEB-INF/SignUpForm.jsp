<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	
	<title>Sign Up</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
    <meta name="author" content="">

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
</style>

</head>
<body>
	<div class = "main">
		<h1 class = "header">${headerMessage}</h1>
		<br>
		<h3>Sign Up Form</h3>
		<br>
		<form:form action="/RNA/signupSuccess" commandName="userSignUp" method="post">
			<table>
			
				<tr><td id='1'>Name : </td><td><input type="text" name="name" /></td><td><form:errors path="name" class="error"/></td></tr>
				<tr><td id='2'>Mobile No. : </td><td><input minlength=10 maxlength=10 type="text" name="mobile" /></td><td><form:errors path="mobile" class="error"/><span class="error">${msg3}</span></td></tr>
				<tr><td>Username :    		</td>  <td>       <input type="text" name="username" /></td><td><form:errors path="username" class="error"/><span class="error">${msg1}</span></td></tr>
				<tr><td id='3'>Email (optional) :    		</td>  <td id='4'>       <input type="text" name="email" />    </td> <td>       <form:errors path="email" class="error"/><span class="error">${msg2}</span></td></tr>
			<tr><td>Password :    		</td>  <td>       <input type="password" name="password" />    </td> <td>       <form:errors path="password" class="error"/>   </td>  </tr>

		 
			<tr>
				
				<td>Street:     </td> <td>        <input type="text" name="userAddress.street" /></td></tr> 
				<tr><td>City:     </td> <td>        <input type="text" name="userAddress.city" /></td><td>       <form:errors path="userAddress.city" class="error"/> </td></tr>
				<tr><td>Pincode:     </td> <td>      <input minlength=6 maxlength=6 type="text" name="userAddress.pincode" /></td></tr>
				<tr><td>Country:     </td> <td>      <input type="text" name="userAddress.country" /></td><td>       <form:errors path="userAddress.country" class="error"/> </td>
			</tr>
			<tr><td>User Type : </td><td>Company : <input type="radio"  name="userType" value="company" /> Transporter : <input type="radio" checked name="userType" value="transporter" /></td><td></td></tr>
			<tr><td><input  type="submit" value="Sign Up" /></td><td><a href="/RNA/login">Already a member, Login !</a></td></tr>
		</table>
		</form:form>
 	</div>
</body>
</html>

