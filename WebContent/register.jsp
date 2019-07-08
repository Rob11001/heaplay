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

<h2>Registrazione</h2>
<hr class="hr-form">
<form action="register" onsubmit="return validateForm()" method="post">

	<label for="username">Username<br/></label>
	<input class="form-input-text" type="text" name="username" value="<%=username%>"><br/>
	
	<label for="email">E-Mail<br/></label>
	<input class="form-input-text" type="text" name="email" value="<%=email%>"><br/>
	
	<label for="password">Password<br/></label>
	<input class="form-input-text" type="password" name="password" value=""><br/>
	
	<label for="repeat-password">Ripeti Password<br/></label>
	<input class="form-input-text" type="password" name="repeat-password" value=""><br/>
	
	<span class="form-error">
	<%
		if (error != null) {
	%>
	<%=error%>
	<%	} %>
	</span><br/>
	
	<button class="form-input-button" type="submit">Registrati</button>
</form>

<script src="${pageContext.servletContext.contextPath}/js/validate.js"></script>