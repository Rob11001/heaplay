<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%
	UserBean user = (UserBean)session.getAttribute("user");
%>
<header>
	<div class="menu-bar">
		<nav class="links">
			<a href="<%=response.encodeURL("home") %>">Home</a>
			<a href="#">Link 2</a>
			<a href="#">Link 3</a>
		</nav>
		<div class="search">
			<input class="search-box" type="text" name="q" placeholder="Cerca..."><button class="search-button"><i class="fa fa-search"></i></button>
		</div>
		<nav class="user">
			<%if(session.getAttribute("user") == null) {%>
				<a href=<%=response.encodeURL("login")%>>Login</a>
				<a href="<%=response.encodeURL("register")%>">Registrati</a>
			<%} else { %>
				<div class="dropdown">
					<a class="dropbtn" href="#"><%=user.getUsername()%></a>
					<div class="dropdown-content">
						<a href="<%=response.encodeURL("user/" + user.getUsername()) %>">Area Utente</a>
						<a href="<%=response.encodeURL("logout")%>">Logout</a>
					</div>
				</div>
			<%} %>
		</nav>
	</div>
</header>