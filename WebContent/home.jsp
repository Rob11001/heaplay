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
</head>

<body>
<%@ include file="WEB-INF/views/header.jsp" %> 
<br>
<p>Sei nella HomePage</p>
<br>
<% if(user != null) {%>
	<div style="color:red" >
		<p>Benvenuto <%=user.getUsername() %></p>
	</div>
	<a href="<%=response.encodeURL("upload")%>">Carica</a>
<% }	%>
</body>
</html>