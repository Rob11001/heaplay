<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    session="true"
    %>
<!DOCTYPE html>
<html>
<%
	UserBean user = (UserBean)session.getAttribute("user");
%>
<head>
<meta charset="ISO-8859-1">
<title>Homepage</title>
<%@ include file="WEB-INF/views/links.jsp" %>
</head>


<body>
<%@ include file="WEB-INF/views/header.jsp" %> 
<p>Sei nella HomePage</p>
<% if(user != null) {%>
	<div style="color:red" >
		<p>Benvenuto <%=user.getUsername() %></p>
	</div>
<%} %>
</body>
</html>