<div class="song">
	<audio preload="metadata" class="audio" ontimeupdate="updateCurrentTime(this)">
		<!-- Problemi con il caricamento dell'audio -->
		<%if(track != null) {%>
		<source
			src="../getAudio?id=<%=track.getId()%>&extension=<%=track.getTrackExt()%>"
			type="audio/<%=track.getTrackExt().substring(1)%>">
		<%} %>
	</audio>

	<div class="song-image">
		<%if(track != null) {%>
		<!-- Conterrà l'immagine della track -->
		<img width="100px"
			src="../getImage?id=<%=track.getId()%>&extension=<%=track.getImageExt()%>"
			alt="Errore">
		<%} %>
	</div>
	
	<div class="song-info">
		<div class="info">
			<span><a href="/heaplay/<%=track.getAuthor()%>/<%=track.getName()%>"><%=track.getName()%></a></span> <br>
			<span>Di <a href="/heaplay/<%=track.getAuthor()%>"><%=track.getAuthor()%></a></span> <!-- Problema per trovare l'autore -->
		</div>
		<div class="controls">
			<button class="play">
				<i class="fa fa-play color-white"></i>
			</button>
<!-- 			<button class="pause"> -->
<!-- 				<i class="fa fa-pause color-white"></i> -->
<!-- 			</button> -->
			<button class="slidebar">
				<span class="song-time">00:00</span> 
				<input type="range"
					name="slider" step="1" class="slider slider-bar"
					onchange="setCurrentTime(this)" value="0" min="0"
					max=<%=track!=null ? track.getDuration() : 100%>>
				<%if(track != null) {%>
				<span><%=String.format("%2d:%2d", track.getDuration()/60,track.getDuration()%60)%></span>
				<%} %>
			</button>
			<button class="volume-button">
				<i class="fa fa-volume-up"></i>
				<input type="range" name="volume" step=".1" class="slider volume"
				onchange="setVolume(this)" value="1" min="0" max="1">
			</button>
		</div>
	</div>
	
</div>