<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<header>
	<nav>
		<div class="search">
			<form action="" method="get">
				<input type="text" name="q" placeholder="Cerca..."> 
				<input type="submit" value="Cerca">
			</form>
		</div>
		<div class="login">
			<%if(session.getAttribute("user") == null) {%>
				<a href=<%=response.encodeURL("login")%>>Login</a>.&nbsp;<br>Utente nuovo? 
				<a href="<%=response.encodeURL("register")%>">Registrati</a>.<br>
			<%} else { %>
				<a href="<%=response.encodeURL("logout")%>">Logout</a><br>
			<%} %>
		</div>
	</nav>
</header>