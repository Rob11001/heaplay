<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean currentUser = ((UserBean)session.getAttribute("user"));
	ArrayList<TrackBean> listOfTracks = (ArrayList<TrackBean>)request.getAttribute("tracks");
	Integer begin = (Integer)request.getAttribute("begin");
	Integer number = (Integer)request.getAttribute("numberOfTracks");
%>


<img id="user-image" src="/heaplay/getImage?id=<%=currentUser.getId()%>&user=true" onclick = "inputFile($('#image'))"  width="150px">
<span class="userName"><%=currentUser.getUsername()%></span>

<form action="/heaplay/uploadImage" name="fileUpload" method="POST" enctype="multipart/form-data"  >
	<input id = "image" type="file" name ="image" accept="image/*" class ="hidden">
	<input type="submit" id="srcImg" value="Carica" class="hidden"></input>
</form>

<nav class="content-nav">
	<a class="trackButton selected" onclick="selection(this,$('.ownedTrackButton'),$('.user-ownedtracks'),$('.user-tracks'))" href="#">Caricati</a>
	<a class="ownedTrackButton" onclick="selection(this,$('.trackButton'),$('.user-tracks'),$('.user-ownedtracks'))" href="#">Acquistati</a>
</nav> 

<div class="user-tracks">
	<%if(listOfTracks.size() == 0) { %>
		<p>Non sono presenti brani</p>
	<%} %>
	
	<%for(int i=0;i<listOfTracks.size();i++) {	//Problema al numero massimo di track che posso mantenere in player in una pagina --> Capire come poter passare ad un altra pagina  per vedere le restanti
		TrackBean track = listOfTracks.get(i);	
	%>
		<div>
			<%@ include file="/_player.jsp"%>
			<button onclick="addToPlaylist(this)">Aggiungi ad una playlist</button>
		</div>
	<%} %>
	<br>
	<form action="/heaplay/user/<%=currentUser.getUsername()%>" method="POST">
		<input type="hidden" value="<%=begin%>" name="begin" id="currentPage">
		<%for( int i= 0; i< number; i+=5) {%>
			<input type="submit" value="<%=i/5+1%>" onclick="beginValue(this)" id="<%=i%>">	
		<%} %>
	</form>
</div>

<div class="user-ownedtracks hidden">

</div>

<div class="playlist-form hidden">  
  	<form class="playlist-form-content animate" action="/heaplay/uploadPlaylist" method="POST">
    	<div class="container" >
    		<span onclick="showHide($('.playlist-form'))" class="close" title="Chiudi">&times;</span>
		</div>
    	<div class="container-playlist">
    		<label for="playlist-selection">Seleziona la playlist: </label>
    		<input id="playlist-selection" type="text" list="listOfPlaylist" name="playlistName" onkeyup="autocompletePlaylist(this,$('#listOfPlaylist'))">
    		<datalist id="listOfPlaylist">
    		</datalist>
    		<input type="hidden" name="track_id" id="track_id">
    		<input type="submit" value="Aggiungi">
    	</div>
		
    	<div class="container">
    	  <button class="close-button" type="button" onclick="showHide($('.playlist-form'))">Cancel</button>
    	</div>
  </form>
</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/playlist.js" ></script>
