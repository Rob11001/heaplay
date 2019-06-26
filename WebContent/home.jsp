<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
%>
<br>
<p>Sei nella HomePage</p>
<br>
<% if(user != null) {%>
	<div style="color:red" >
		<p>Benvenuto <%=user.getUsername() %></p>
		<p>Audio</p>
	
		<div id="player"> <!-- Classe del player che possiamo mettere in una jsp a parte -->
			<div>
				<audio preload="metadata" id="audio" ontimeupdate="updateCurrentTime(this)" >				<!-- Problemi con il caricamento dell'audio -->
					<source src="getAudio?id=8&extension=.mp3" type="audio/mp3"> 		
	 			</audio>
	 		</div>
			
			<div class="controls">	<!-- Sar� la classe aventi i controlli del player -->
				<button class="back"><img src="images/back-button.png" width="25px"></button>
				<button class="play"><img src="images/play-button.png" width="25px"></button>
				<button class="forward"><img src="images/forward-button.png" width="25px"></button>
				<button class="pause"><img src="images/pause-button.png" width="25px"></button>
				<button class="replay"><img src="images/replay-button.png" width="25px"></button>
				<br>
				<button class="slidebar">
					<span id="time">00:00</span>
					<input type="range" name ="slider" step="1" id="slider" onchange="setCurrentTime(this)" value="0" min="0" >
				</button>
				<button class="volume-up">Volume+</button>
				<button class="volume-down">Volume-</button>
				<button class="load">Carica</button>
			</div>
			<div class="image">			<!-- Conterr� l'immagine della track -->
				<img src="" alt="">
				<a></a>
			</div>
			
		</div>
	</div>
	<a href="<%=response.encodeURL("upload")%>">Carica</a>
<% }	%>

	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script> 
	<script src="${pageContext.servletContext.contextPath}/js/uploadAudio.js" ></script>