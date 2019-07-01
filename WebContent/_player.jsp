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
	<div class="author">
		<span><%=track.getName()%></span> <span><%=track.getAuthor()%></span>
		<!-- Problema per trovare l'autore -->
	</div>
	<div class="controls">
		<!-- Sarà la classe aventi i controlli del player -->
		<button class="back">
			<img src="../images/back-button.png" width="25px">
		</button>
		<button class="play">
			<img src="../images/play-button.png" width="25px">
		</button>
		<button class="forward">
			<img src="../images/forward-button.png" width="25px">
		</button>
		<button class="pause">
			<img src="../images/pause-button.png" width="25px">
		</button>
		<button class="replay">
			<img src="../images/replay-button.png" width="25px">
		</button>
		<button class="slidebar">
			<span class="song-time">00:00</span> <input type="range"
				name="slider" step="1" class="slider-bar"
				onchange="setCurrentTime(this)" value="0" min="0"
				max=<%=track!=null ? track.getDuration() : 100%>>
			<%if(track != null) {%>
			<span><%=String.format("%2d:%2d", track.getDuration()/60,track.getDuration()%60)%></span>
			<%} %>
		</button>
		<button class="volume-button">
			<img src="../images/volume-button.png" width="25px">
		</button>
		<input type="range" name="volume" step=".1" class="volume"
			onchange="setVolume(this)" value="1" min="0" max="1">
	</div>
</div>