<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty destinationList}">
	<option selected disabled>Chose ${location}</option>
	<c:forEach var="listValue" items="${destinationList}">
		<option value = "${listValue.place}" >${listValue.place}</option>
	</c:forEach>
</c:if>