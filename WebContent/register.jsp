<%
	String error = (String) request.getAttribute("errorMessage");
	String email = (String) request.getAttribute("email");
	String username = (String) request.getAttribute("username");
	if(email == null)
		email = "";
	if(username == null)
		username = "";
	/*String previousUrl = request.getRequestURI();
	if(!previousUrl.startsWith("/login"))
		session.setAttribute("afterLoginRedirect", previousUrl);
	*/
%>
<script src="${pageContext.servletContext.contextPath}/js/validate.js"></script>

<h2>Registrazione</h2>
<hr class="hr-form">
<form action="register" onsubmit="return validateRegister(this)" method="post">

	<label for="username">Username<br/></label>
	<input class="form-input-text" type="text" name="username" value="<%=username%>" required><br/>
	
	<label for="email">E-Mail<br/></label>
	<input class="form-input-text" type="text" name="email" value="<%=email%>" required><br/>
	
	<label for="password">Password<br/></label>
	<input class="form-input-text" type="password" name="password" value="" required><br/>
	
	<input class="form-input-submit" type="submit" value="Register">
</form>
<%	if( error != null) {%>
<span class="form-error"><%=error%></span>
<%	} %>