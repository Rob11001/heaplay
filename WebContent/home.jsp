<%@page import="com.heaplay.model.ConnectionPool"%>
<%@page import="com.heaplay.model.dao.TrackDao"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	TrackBean track = (TrackBean)session.getAttribute("currentTrack");
	TrackDao dao = new TrackDao((ConnectionPool)application.getAttribute("pool"));
	track=(TrackBean)dao.doRetrieveAll(null).get(1);
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
					<%if(track != null) {%>
						<source src="getAudio?id=<%=track.getId()%>&extension=<%=track.getTrackExt()%>" type="audio/<%=track.getTrackExt().substring(1)%>"> 		
	 				<%} %>
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
					<input type="range" name ="slider" step="1" id="slider" onchange="setCurrentTime(this)" value="0" min="0"  max=<%=track!=null ? track.getDuration() : 100%>>
				</button>
				<button class="volume-up">Volume+</button>
				<button class="volume-down">Volume-</button>
				<button class="load">Carica</button>
			</div>
			<div class="image">
				<%if(track != null) {%>			<!-- Conterrà l'immagine della track -->
					<img src="getImage?id=<%=track.getId()%>?extension=<%=track.getImageExt()%>" alt="">
				<%} %>
				<a></a>
			</div>
			
		</div>
	</div>
	<a href="<%=response.encodeURL("upload")%>">Carica</a>
<% }	%>
	
	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script> 
	<script src="${pageContext.servletContext.contextPath}/js/uploadAudio.js" ></script>