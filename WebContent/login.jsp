<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%
	String error = (String) request.getAttribute("errorMessage");
	String email = (String) request.getAttribute("email");
	if(email == null)
		email="";
	/*String previousUrl = request.getRequestURI();
	if(!previousUrl.startsWith("/login"))
		session.setAttribute("afterLoginRedirect", previousUrl);
	*/
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
<%@ include file="WEB-INF/views/header.jsp" %> 
<br>
<form action="login" method="post">
	<label for="email">E-Mail: </label>
	<input type="text" name="email" value="<%=email%>" required><br/>
	<label for="password">Password: </label>
	<input type="password" name="password" value="" required><br/>
	<input type="submit" value="Login">
</form>
<%	if( error != null) {%>
		<%=error%>
<%	} %>	
</body>
</html>