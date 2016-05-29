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
	<title>Admin</title>
	
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
	.admin{
		display: none;
		margin-top: 2%;
	}
	.route{
		display: none;
	}
		
</style>
</head>
<body>
	<div class = "main">
		<h1 class = "header">${headerMessage}</h1>
		<br>
		<div class="submain">
			<ul id="admin">
				<li id="Route">Add Route</li>
				<div class="admin">
				
					<table>
					<tr><td>Name of the company :</td>
					<td><select id="companyName">
						<option value="chose1" selected disabled>Chose Company</option>
						<c:if test="${not empty companyList}">
							<c:forEach var="listValue" items="${companyList}">
								<option value = "${listValue.companyId}" >${listValue.name}</option>
							</c:forEach>
						</c:if>
					</select>
					</td>
					<td>
						<span id="companyNameError" class="error"></span>
					</td></tr>
					<tr>
						<td>Source : </td>
						<td>
							<select  id="sourceName">
								<option value="chose2" selected disabled>Chose Source</option>
								<c:if test="${not empty sourceList}">
									<c:forEach var="listValue" items="${sourceList}">
										<option value = "${listValue.locationName}" >${listValue.locationName}</option>
									</c:forEach>
								</c:if>
								<option value="other2">Other..</option>
							</select>
						</td>
						<td class="route">
							<input type = "text" id="sourceAdd" value="" />
						</td>
						<td><span id="sourceError" class="error"></span></td>
						<td class="route">
							<span id="listSource" style="cursor: pointer; text-decoration: underline; color: blue;">Chose from the list</span>
						</td>
						
					</tr>
					<tr>
						<td>Destination: </td>
						<td>
							<select  id="destinationName">
								<option value="chose3"  selected disabled>Chose Destination</option>
								<c:if test="${not empty destinationList}">
									<c:forEach var="listValue" items="${destinationList}">
										<option value = "${listValue.locationName}" >${listValue.locationName}</option>
									</c:forEach>
								</c:if>
								<option value="other3">Other..</option>
							</select>
						</td>
						<td class = "route">
							<input type= "text" name='destinationName' id="destinationAdd" value="" />
						</td>
						<td><span id="destinationError" class="error"></span></td>
						<td class="route">
							<span id="listDestination" style="cursor: pointer; text-decoration: underline; color: blue;">Chose from the list</span>
						</td>
					</tr>
					<tr><td>Type of truck : </td>
					<td>
						<c:if test="${not empty truckTypeList}">
							<c:forEach var="listValue" items="${truckTypeList}">
									<input type="checkbox" name='trucktype' value="${listValue.truckType}"/>${listValue.truckType}
							</c:forEach>
						</c:if>
					</td>
					<td><span id="truckTypeError" class="error"></span></td>
					</tr>
					</table>
					<br>
					<input type="submit" id="addRoute" value="submit">
					<br>
					<span class="message"></span>
				
				</div>
				<li id="adminViewTruck">View Trucks</li>
				<div class="admin"></div>
				<li id = "adminViewTransporter" >View Transporters</li>
				<div class="admin"><br>
					
				</div>
				<li id="adminViewCompany">View Company</li>
				<div class="admin"><br>
					
				</div>
			</ul>
		</div>
 	</div>
</body>
</html>