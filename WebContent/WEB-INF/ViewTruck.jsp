<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table>
	<c:if test="${not empty truckList}">
		<c:forEach var="listValue" items="${truckList}">
			<tr><td>${listValue.name}</td><td>${listValue.registrationNumber}</td><td>${listValue.truckType}</td><td class="booking" id="${listValue.truckId}">View Bookings</td></tr>
		</c:forEach>
	</c:if>
</table>