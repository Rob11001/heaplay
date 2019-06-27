<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
	<div class="menu-bar">
		<nav class="links">
			<a href="<%=response.encodeURL("home") %>">Home</a>
			<a href="#">Link 2</a>
			<a href="#">Link 3</a>
		</nav>
		<div class="search">
			<form action="" method="get">
				<input class="search-box" type="text" name="q" placeholder="Cerca..."><button class="search-button" type="submit"><i class="fa fa-search"></i></button>
			</form>
		</div>
		<nav class="user">
			<%if(session.getAttribute("user") == null) {%>
				<a href=<%=response.encodeURL("login")%>>Login</a>
				<a href="<%=response.encodeURL("register")%>">Registrati</a>
			<%} else { %>
				<a href="<%=response.encodeURL("logout")%>">Logout</a>
			<%} %>
		</nav>
	</div>
</header>