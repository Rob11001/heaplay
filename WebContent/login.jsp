<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
<meta charset="UTF-8">
<title>Login</title>
<%@ include file="WEB-INF/views/links.jsp"%>
</head>
<body>
		<jsp:include page="WEB-INF/views/header.jsp" />
		<div class="content-wrapper">

			<form action="login" method="post">
				<label for="email">E-Mail: </label> <input type="text" name="email"
					value="<%=email%>" required><br /> <label for="password">Password:
				</label> <input type="password" name="password" value="" required><br />
				<input type="submit" value="Login">
			</form>
			<%
				if (error != null) {
			%>
			<%=error%>
			<%	} %>

		</div>




</body>
</html>