<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"
    session="true"
    %>
<!DOCTYPE html>
<html>
<%
	UserBean user = (UserBean)session.getAttribute("user");
%>
<head>
<meta charset="ISO-8859-1">
<title>Homepage</title>
</head>
<body>
<%@ include file="WEB-INF/views/header.jsp" %> 
<br>
<p>Sei nella HomePage</p>
<br>
<% if(user != null) {%>
	<div style="color:red" >
		<p>Benvenuto <%=user.getUsername() %></p>
		<p>Audio</p>
	
		<audio controls preload="auto" id="audio">				<!-- Problemi con il caricamento dell'audio -->
 			<source src="getAudio?id=8&extension=.mp3" type="audio/mp3" > 
		</audio>
	
	</div>
	<a href="<%=response.encodeURL("upload")%>">Carica</a>
<% }	%>

	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script> 
	 <script src="${pageContext.servletContext.contextPath}/js/uploadAudio.js" ></script>
</body>

</html>