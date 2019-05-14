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
			<a href=<%=response.encodeURL("login")%>>Login</a>.&nbsp;Utente nuovo? <a href="#">Registrati</a>.
		</div>
	</nav>
</header>