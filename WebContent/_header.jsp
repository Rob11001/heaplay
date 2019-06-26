<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
	<div class="menu-bar-wrapper">
		<div class="menu-bar-links">
			<a href="<%=response.encodeURL("home") %>">Home</a>
		</div>
		<div class="menu-bar-search">
			<form action="" method="get">
				<input type="text" name="q" placeholder="Cerca..."> 
				<input type="submit" value="Cerca">
			</form>
		</div>
		<div class="menu-bar-user">
			<%if(session.getAttribute("user") == null) {%>
				<a href=<%=response.encodeURL("login")%>>Login</a>.&nbsp;Utente nuovo? 
				<a href="<%=response.encodeURL("register")%>">Registrati</a>.
			<%} else { %>
				<a href="<%=response.encodeURL("logout")%>">Logout</a>
			<%} %>
		</div>
	</div>
</header>