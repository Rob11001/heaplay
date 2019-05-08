<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%

	String previousUrl = request.getRequestURI();
	if(!previousUrl.startsWith("/login"))
		session.setAttribute("afterLoginRedirect", previousUrl);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
<%@ include file="WEB-INF/views/links.jsp" %>
</head>
<body>
<%@ include file="WEB-INF/views/header.jsp" %> 
<form action="/login" method="post">
	<label for="email">E-Mail: </label>
	<input type="text" name="email" value="" required><br/>
	<label for="password">Password: </label>
	<input type="password" name="password" value="" required><br/>
	<input type="submit" value="Login">
</form>
</body>
</html>