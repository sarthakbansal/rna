<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
	"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>LoginSuccess</title>
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
</style>
</head>
<body>
	<div class = "main">
		<h1 class = "header">${headerMessage}</h1>
		<br><br>
		<h2>${msg}</h2><br>
			<h4>Your Details</h4>
			<table>
				<tr>
					<td>Name :</td>
					<td> ${userSignUp.name} </td>
				</tr>
				<tr>
					<td>Mobile No. :</td>
					<td>${userSignUp.mobile}</td>
				</tr>
				<tr>
					<td>Username :</td>
					<td>${userSignUp.username}</td>
				</tr>
				<tr>
					<td>Email :</td>
					<td>${userSignUp.email}</td>
				</tr>
				<tr>
					<td>Student Address :</td>
					<td>Street: ${userSignUp.userAddress.street}
					     City: ${userSignUp.userAddress.city}
					    Pincode: ${userSignUp.userAddress.pincode}
					    Country: ${userSignUp.userAddress.country}
					</td>
				</tr>
			</table>
	</div>
</body>
</html>
