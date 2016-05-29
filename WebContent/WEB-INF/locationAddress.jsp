<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<option value="chose" selected disabled>Chose Address</option>
<c:if test="${not empty locationAddressList}">
	<c:forEach var="listValue" items="${locationAddressList}">
		<option value = "${listValue.id}" >${listValue.address}</option>
	</c:forEach>
</c:if>