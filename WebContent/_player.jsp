<%@page import="com.heaplay.model.beans.UserBean"%>
<%UserBean user = (UserBean)session.getAttribute("user"); 
%>

<div class="song">
	<audio preload="none" class="audio" ontimeupdate="updateCurrentTime(this)">
		<!-- Problemi con il caricamento dell'audio -->
		<% if(track != null) {%>
		<source
			src="/heaplay/getAudio?id=<%=track.getId()%>&extension=<%=track.getTrackExt()%>"
			type="audio/<%=track.getTrackExt().substring(1)%>">
		<%} %>
	</audio>

	<div class="song-image">
		<%if(track != null) {%>
		<!-- Conterrà l'immagine della track -->
		<img 
			src="/heaplay/getImage?id=<%=track.getId()%>&extension=<%=track.getImageExt()%>"
			alt="Errore">
		<%} %>
	</div>
	
	<div class="song-content">
		<div class="song-info">
			<button class="play">
					<i class="fa fa-play color-white"></i>
			</button>
			<div>
				<span class="author"><a href="/heaplay/user/<%=track.getAuthorName()%>"><%=track.getAuthorName()%></a></span><br>
				<span class="song-name"><a href="/heaplay/user/<%=track.getAuthorName()%>/<%=track.getName().replaceAll("\\s","")%>?id=<%=track.getId()%>"><%=track.getName()%></a></span>
			</div>
		</div>
		
		<div class="controls">
			<table>
				<tr>
					<td><span class="song-time">00:00</span></td>
					<td><input type="range"
					name="slider" step="1" class="slider slider-bar"
					onchange="setCurrentTime(this)" value="0" min="0"
					max=<%=track!=null ? track.getDuration() : 100%>></td>
					<td><%if(track != null) {%>
						<span><%=String.format("%2d:%2d", track.getDuration()/60,track.getDuration()%60)%></span>
					<%} %></td>
				</tr>
				<tr>
					<td><i class="fa fa-volume-down"></i></td>
					<td><input type="range" name="volume" step=".1" class="slider volume"
						onchange="setVolume(this)" value="1" min="0" max="1"></td>
					<td><i class="fa fa-volume-up"></i></td>
				</tr>
			</table>
		</div>
		
		<span class="like"><i class="fa fa-thumbs-up"></i></span>
		
	</div>	
</div>