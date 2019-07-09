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

	<label for="username">Username*<br/></label>
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-user"></i></span>
		<input class="form-input-text" type="text" name="username" value="<%=username%>">
	</div>
	
	
	<label for="email">E-Mail*<br/></label>
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-envelope"></i></span>
		<input class="form-input-text" type="text" name="email" value="<%=email%>"><br/>
	</div>
	
	<label for="password">Password*<br/></label>
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-key"></i></span>
		<input class="form-input-text" type="password" name="password" value=""><br/>
	</div>
	
	<label for="repeat-password">Ripeti Password*<br/></label>
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-key"></i></span>
		<input class="form-input-text" type="password" name="repeat-password" value=""><br/>
	</div>
	
	<span class="form-error">
	<%
		if (error != null) {
	%>
	<%=error%>
	<%	} %>
	</span><br/>
	
	<button type="submit">Registrati</button>
</form>

<script src="${pageContext.servletContext.contextPath}/js/validate.js"></script>