<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${not empty lists}">
	<option value="chose" selected disabled>Chose truck type</option>
	<c:forEach var="listValue" items="${lists}">
		<option value = "${listValue.truckType}" >${listValue.truckType}</option>
	</c:forEach>
</c:if>