<%@page import="java.util.ArrayList"%>
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
	
		<div id="player"> <!-- Classe del player che possiamo mettere in una jsp a parte -->
			<div>
				<audio preload="none" id="audio" ontimeupdate="updateCurrentTime(this)" >				<!-- Problemi con il caricamento dell'audio -->
					<source src="getAudio?id=8&extension=.mp3" type="audio/mp3"> 		
	 			</audio>
	 		</div>
			
			<div class="controls">	<!-- Sarà la classe aventi i controlli del player -->
				<button class="back"><img src="images/back-button.png" width="25px"></button>
				<button class="play"><img src="images/play-button.png" width="25px"></button>
				<button class="forward"><img src="images/forward-button.png" width="25px"></button>
				<button class="pause"><img src="images/pause-button.png" width="25px"></button>
				<button class="replay"><img src="images/replay-button.png" width="25px"></button>
				<br>
				<button class="slidebar">
					<span id="time">00:00</span>
					<input type="range" name ="slider" step="1" id="slider" onchange="setCurrentTime(this)" value="0">
				</button>
				<button class="volume-up">Volume+</button>
				<button class="volume-down">Volume-</button>
				<button class="load">Carica</button>
			</div>
			<div class="image">			<!-- Conterrà l'immagine della track -->
				<img src="" alt="">
				<a></a>
			</div>
			
		</div>
	</div>
	<a href="<%=response.encodeURL("upload")%>">Carica</a>
<% }	%>

	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script> 
	 <script src="${pageContext.servletContext.contextPath}/js/uploadAudio.js" ></script>
</body>

</html>