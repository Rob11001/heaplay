<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>  
<%
	UserBean user = (UserBean)session.getAttribute("user");
%>
<header>
	<div class="menu-bar">
		<nav class="links">
			<a href="<%=response.encodeURL("/heaplay/home") %>">Home</a>
			<a href="<%=response.encodeURL("/heaplay/library") %>">Libreria</a>
		</nav>
		<div class="search">
			<select class="search-select" name="filter">
				<option value="track">Brani</option>
				<option value="tag">Tags</option>
				<option value="playlist">Playlist</option>
				<option value="user">Utenti</option>
			</select><!-- 
			--><input class="search-box" type="text" name="q" placeholder="Cerca..." list="suggestions" onkeyup="autocompleteSearch(this,$('#suggestions'))" onkeypress="searchOnEnterButton(event)"><!--
			--><datalist id="suggestions"></datalist><button class="search-button"><i class="fa fa-search"></i></button>
		</div>
		<nav class="user">
			<%if(session.getAttribute("user") == null) {%>
				<a href=<%=response.encodeURL("/heaplay/login")%>>Login</a>
				<a href="<%=response.encodeURL("/heaplay/register")%>">Registrati</a>
			<%} else { %>
				<div class="dropdown">
					<div class="dropbtn">
						<a href="#"><%=user.getUsername()%></a>
					</div>					
					<div class="dropdown-content">
						<a href="<%=response.encodeURL("/heaplay/user/" + user.getUsername()) %>">Area Utente</a>
						<a href="<%=response.encodeURL("/heaplay/upload")%>">Carica</a>
						<a href="<%=response.encodeURL("/heaplay/cart")%>">Carrello</a>
						<a href="<%=response.encodeURL("/heaplay/logout")%>">Logout</a>
					</div>
				</div>
			<%} %>
		</nav>
	</div>
</header>