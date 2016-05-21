<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


	<spring:url value="/resources/js/function.js" var="functionJS" />
	<script src = "${functionJS}" ></script>

<table  cellpadding="10" style="border-collapse: collapse;">
	<c:if test="${not empty list}">
		<h3>Company Details</h3>
		<c:forEach var="listvalue" items="${list}" varStatus="rowStatus">
			<c:if test="${rowStatus.count < 2}">
				<tr><td>Company Name : </td><td class="companyDetails" contenteditable="false">${listvalue.name}</td><td class="error"></td></tr>
				<tr><td>Contact : </td><td class="companyDetails" contenteditable="false">${listvalue.mobile}</td><td class="error"></td></tr>
				<tr><td>Email : </td><td class="companyDetails" contenteditable="false">${listvalue.email}</td><td class="error"></td></tr>
				<tr><td>Address : </td><td class="companyDetails" contenteditable="false">${listvalue.address}</td><td class="error"></td></tr>
				<tr><td colspan="2" ><span class='edit' name="edit">EDIT</span><span class='edit' style="display: none;" name="save">SAVE</span>&nbsp &nbsp<span style="display: none;" class='edit' name="cancel">CANCEL</span></td></tr>
			</c:if>
		</c:forEach>
	</c:if>
</table><br>
<table border="1" cellpadding="10" style="border-collapse: collapse;">
	<c:if test="${not empty list}">
		<tr><td>Routes</td>
		<c:forEach var="listvalue" items="${list}" >
			<td id = "${listvalue.routeId}">${listvalue.sourceId} - ${listvalue.destinationId}</td>
		</c:forEach>
		</tr>
	</c:if>
</table>