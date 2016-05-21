<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Transporter</title>
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
		
		ul li {
	   		
	   		list-style-type: none; 
	   		text-decoration: underline;
	   		margin-top: 2%;
	   		cursor: pointer;
		}
		
		.error { 
			color: red; font-weight: bold; 
		}
		
		table {
	   		margin: 0 auto;
	   		height: 50px; 
		}
		.transporter{
			display: none;
			margin-top: 2%;
		}
		h5{
			float: right;
			color: purple;
			text-decoration: underline; 
		}
		.booking{
			cursor: pointer;
			color: blue;
			text-decoration: underline; 
		}	
	</style>
		
</head>
<body>
	<div class = "main">
		<h5>Welcome ${username}! You have logged in successfully.</h5><br>
		<h1 class = "header">${headerMessage}</h1>
		<br>
		<div class="submain">
			<ul id = "transport">
				<li id="truckRegister">Register Truck</li>
				<div class="transporter">
				
					<table>
						<tr><td>Truck Registration Number :</td>
							<td><input type="text" id="reg-no" value="" /></td>
							<td><span id="regName" class="error"></span></td>
						</tr>
						<tr><td>Type of truck : </td>
							<td>
								<select name='dest' id="trucktype">
									<option value="chose" selected disabled>Chose truck type</option>
									<c:if test="${not empty lists5}">
										<c:forEach var="listValue" items="${lists5}">
											<option value = "${listValue.truckType}" >${listValue.truckType}</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
							<td><span id="truckType" class="error"></span></td>
						</tr>
						<tr>
							<td>Source : </td>
							<td>
								<select name='source' id="source">
									<option value="chose2" selected disabled>Chose Source</option>
									<c:if test="${not empty lists3}">
										<c:forEach var="listValue" items="${lists3}">
											<option value = "${listValue.locationName}" >${listValue.locationName}</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
							<td><span id="sourceName" class="error"></span></td>
						</tr>
						<tr>
							<td>Destination: </td>
							<td>
								<div id="div1">
								<select name='dest' id="dest">
									<option value="chose3" selected disabled>Chose Destination</option>
									<c:if test="${not empty lists4}">
										<c:forEach var="listValue" items="${lists4}">
											<option value = "${listValue.locationName}" >${listValue.locationName}</option>
										</c:forEach>
									</c:if>
								</select>
								</div>
								<div id ="div2" style="display : none;">
								</div>
							</td>
							<td><span id="destinationName" class="error"></span></td>
						</tr>
						<tr>
							<td>PAN Number : </td><td><input type="text" id="pan" value="" /></td>
							<td><span id="panName" class="error"></span></td>
						</tr>
					</table>
					<table>
						<h4>Driver's details</h4>
						<tr>
							<td>Name : </td>
							<td><input type="text" id="name" value="" /></td>
							<td><span id="Name" class="error"></span></td>
						</tr>
						<tr>
							<td>Mobile Number : </td>
							<td><input type="text" id="mobile" value="" /></td>
							<td><span id="Mobile" class="error"></span></td>
						</tr>
					</table>
					<br>
					<input type="hidden" id="userName"value="${username}" />
					<input type="submit" id="registerTruck" value="submit">
					<br>
					<span class="message"></span>
				
				</div>
				<li id="viewTrucks" name="${username}">View Trucks</li>
				<div class="transporter">
				</div>
				<li id="Bookings" ></li>
				<div class="transporter">
				</div>
			</ul>
		</div>
 	</div>
</body>
</html>