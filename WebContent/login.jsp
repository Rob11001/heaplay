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

<h2>Login</h2>
<hr class="hr-form">
<form action="login" onsubmit="return validateForm()" method="post">
	
	<label for="email">E-Mail*<br/></label> 
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-user"></i></span>
		<input class="form-input-text" type="text" name="email"  value="<%=email%>" >
	</div>
		
	<label for="password">Password*<br/></label>
	<div class="form-field">
		<span class="field-icon"><i class="fa fa-key"></i></span>
		<input class="form-input-text" type="password" name="password" value="">
	</div>
	
	<span class="form-error">
	<%
		if (error != null) {
	%>
	<%=error%>
	<%	} %>
	</span><br/>

	<button class="form-input-button" type="submit">Login</button>
</form>

<script src="${pageContext.servletContext.contextPath}/js/validate.js"></script>