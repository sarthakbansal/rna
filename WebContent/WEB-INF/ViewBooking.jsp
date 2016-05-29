<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table border="1" cellpadding="10" style="border-collapse: collapse;">
	<tr><th>Route</th><th>Booking Date</th><th>Truck Type</th></tr>
	<c:if test="${not empty bookingList}">
		<c:forEach var="listValue" items="${bookingList}">
			<tr>
				<td class="booking" var1="${listValue.routeId}">${listValue.sourceId} - ${listValue.destinationId}</td>
				<td>${listValue.dateOfBooking}</td>
				<td>${listValue.truckType}</td>
			</tr>
		</c:forEach>
	</c:if>
</table>