<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Company</title>
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
		.company{
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
		
		.location{
			display: none;
		}
		
		.destaddAddress, .srcaddAddress{
			cursor: pointer;
			color: blue;
			text-decoration: underline; 
		}	
		
		.destaddressAdd, .srcaddressAdd{
			display: none;
		}
		
		.edit{
			cursor: pointer;
			color: blue;
			text-decoration: underline;
			text-align: center;
		}
	</style>
		
</head>
<body>
	<div class = "main">
		<h5>Welcome ${username}! You have logged in successfully.</h5><br>
		<h1 class = "header">${headerMessage}</h1>
		<br>
		<div class="submain">
			<ul id = "company">
				<li id="bookTruck">Book a Truck</li>
				<div class="company">
				
					<table>
						<tr>
							<td>Source : </td>
							<td>
								<select name='source' id="source">
									<option value="chose2" selected disabled>Chose source</option>
									<c:if test="${not empty lists3}">
										<c:forEach var="listValue" items="${lists3}">
											<option value = "${listValue.locationName}" >${listValue.locationName}</option>
										</c:forEach>
									</c:if>
								</select>
							</td>
							<td><span id="sourceName" class="error"></span></td>
						</tr>
						<tr class="location">
							<td>Location : </td>
								<td class="sourceLocation">
									<select id="sourceAddress">
									</select>	
								</td>
								<td class="sourceLocation">
									<span class="srcaddAddress">Add Location</span>
								</td>
								<td class="srcaddressAdd">
									<input type="text" placeholder="Street" name="street" value=""/><span class='error'></span><br>
									<input type="text" id='srcCity' disabled value=""/><br>
									<input type="text" placeholder="Pin Code" name="pin-code" value=""/><span class='error'></span><br>
									<input type="text" disabled value="INDIA"/>	<br>
									<input type="submit" value="Add" id="addsrcLocation" />
								</td>
								<td class="srcaddressAdd">
									<span class="srcaddAddress">Chose from the list</span>
									<br>
									<span></span>
								</td>
								<td >
									<span id="srcloc" class='error'></span>
								</td>	
						</tr>
						<tr>
							<td>Destination: </td>
							<td >
								<select name='dest' id="dest" disabled>
									<option value="chose3" selected disabled>Chose destination</option>
								</select>
							</td>
							<td><span id="destinationName" class="error"></span></td>
						</tr>
						<tr class="location">
							<td>Location : </td>
								<td class="destLocation">
									<select id="destAddress">
									</select>	
								</td>
								<td class="destLocation">
									<span class="destaddAddress">Add Location</span>
								</td>
								<td class="destaddressAdd">
									<input type="text" placeholder="Street" name="street" value=""/><span class='error'></span><br>
									<input type="text" id='destCity' disabled value=""/><br>
									<input type="text" placeholder="Pin Code" name="pin-code" value=""/><span class='error'></span><br>
									<input type="text" disabled value="INDIA"/>	<br>
									<input type="submit" value="Add" id="addDestLocation" />
								</td>
								<td class="destaddressAdd">
									<span class="destaddAddress">Chose from the list</span>
									<br>
									<span></span>
								</td>
								<td >
									<span id="destloc" class='error'></span>
								</td>	
						</tr>
						<tr>
							<td>Type of truck : </td>
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
							<td>Date of Booking : </td><td><input type="date" id="txtdate" min="" /></td>
							<td><span id="bookingDate" class="error"></span></td>
						</tr>
					</table>
					
					<br>
					<input type="hidden" id="c_id" value="${companyid}" />
					<input type="submit" id="booktruck" value="Book">
					<br>
					<span class="message"></span>
				
				</div>
				<li id="viewBooking">View Bookings</li>
				<div class="company">					
				</div>
				<li id="edit" >View/Edit Details</li>
				<div class="company">
				</div>
			</ul>
		</div>
 	</div>
</body>
</html>