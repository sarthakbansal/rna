<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table>
	<c:if test="${not empty lists}">
		<c:forEach var="listValue" items="${lists}">
			<tr><td>${listValue.name}</td><td>${listValue.mobile}</td><td>${listValue.address}</td></tr>
		</c:forEach>
	</c:if>
</table>
