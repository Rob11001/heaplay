
<%
	String success = (String) request.getAttribute("success");	
	String error = (String) request.getAttribute("errorMessage");
	String email = (String) request.getAttribute("email");
	String username = (String) request.getAttribute("username");
	if(email == null)
		email = "";
	if(username == null)
		username = "";
%>

<%if(success == null) { %>
	<script src="${pageContext.servletContext.contextPath}/js/validate.js"></script>
		
	<h2>Crea amministratore</h2>
	<hr class="hr-form">
	<form action="/heaplay/admin/registerAdmin" onsubmit="return validateRegister(this)" method="post">
		<label for="username">Username<br/></label>
		<input class="form-input-text" type="text" name="username" value="<%=username%>" required><br/>
		
		<label for="email">E-Mail<br/></label>
		<input class="form-input-text" type="text" name="email" value="<%=email%>" required><br/>
		
		<label for="password">Password<br/></label>
		<input class="form-input-text" type="password" name="password" value="" required><br/>
			
		<span class="form-error">
			<%
			if (error != null) {
			%>
			<%=error%>
			<%	} %>
		</span><br/>
		
		<button class="form-input-button" type="submit">Registrati</button>
	</form>
<% } else {%>
	<h3>Creato con successo</h3>
	<a href="<%=response.encodeURL("/heaplay/admin/operation?op=register")%>">Vuoi registrare un altro admin?</a>
<% }%>