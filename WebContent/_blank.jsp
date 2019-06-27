<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String jspPath = (String) request.getAttribute("jspPath"); //Mancano i controlli
	String pageTitle = (String) request.getAttribute("pageTitle");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title><%=pageTitle%></title>
<%@ include file="/_links.jsp"%>
</head>
<body>

	<jsp:include page="/_header.jsp" />
	<div class="content-wrapper">
	
		<jsp:include page="<%=jspPath%>"/>
	
	</div>
	
<!-- 	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script> Da problemi nella upload  -->
</body>
</html>