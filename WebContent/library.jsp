<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean currentUser = ((UserBean)session.getAttribute("user"));
	ArrayList<TrackBean> listOfTracks = (ArrayList<TrackBean>)request.getAttribute("tracks");
	Integer begin = (Integer)request.getAttribute("begin");
	Integer number = (Integer)request.getAttribute("numberOfTracks");
	String owned = (String) request.getAttribute("owned");
%>


<div class="page-header">
	<div class="relative-container">
	<img class="page-image" src="/heaplay/getImage?id=<%=currentUser.getId()%>&user=true" onclick = "inputFile($('#image'))">
		<form class="middle-bottom" action="<%=response.encodeURL("/heaplay/uploadImage")%>" name="fileUpload" method="POST" enctype="multipart/form-data">
			<input id="image" type="file" name ="image" accept="image/*" class ="hidden">
			<button type="submit" id="srcImg"class="hidden">Conferma</button>
		</form>
	</div>
	<span class="page-name"><%=currentUser.getUsername()%></span>
</div>

<nav class="content-nav">
	<a class="trackButton <%=owned==null ? "selected": "" %>"  href="<%=response.encodeURL("/heaplay/library") %>">Caricati</a>
	<a class="ownedTrackButton <%=owned!=null ? "selected": "" %>"  href="<%=response.encodeURL("/heaplay/library?track=owned")%>">Acquistati</a>
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
			<%if(track.isIndexable()) {%>
				<button class="playlist-button" onclick="addToPlaylist(this)"><span>Aggiungi ad una playlist</span></button>
			<% }%>
		</div>
	<%} %>
	<br>
	<form action="<%=response.encodeURL("/heaplay/library"+ ((owned!=null) ? "track=owned" : ""))%>" method="POST">
		<input type="hidden" value="<%=begin%>" name="begin" id="currentPage">
		<%for( int i= 0; i< number; i+=5) {%>
			<input type="submit" value="<%=i/5+1%>" onclick="beginValue(this)" id="<%=i%>">	
		<%} %>
	</form>
</div>

<div class="user-ownedtracks hidden">

</div>

<div class="playlist-form hidden">  
  	<form class="playlist-form-content animate" action="<%=response.encodeURL("/heaplay/uploadPlaylist")%>" method="POST" onsubmit="return validatePlaylist(this)">
    	<div class="container" >
    		<span onclick="showHide($('.playlist-form'))" class="close" title="Chiudi">&times;</span>
		</div>
    	<fieldset>
    		<legend>Playlist</legend>
    		<div class="container-playlist">
    			<div class="playlist-selection">
    				<label for="playlist-selection">Seleziona la playlist : </label>
    				<input id="playlist-selection" type="text" list="listOfPlaylist" name="playlistName" placeholder="Inserire nome playlist" onkeyup="autocompletePlaylist(this,$('#listOfPlaylist'))">
    				<datalist id="listOfPlaylist">
    				</datalist>
    			</div>
    			<div class="radio-buttons">
    				<input id="public-button" class="form-input-radio" type="radio" value="public" name="privacy" placeholder="Pubblica" checked="checked">
    				<label for="public-button" >Pubblica</label>
    				
    				<input id ="private-button" class="form-input-radio" type="radio" value="private" name="privacy" placeholder="Privata">
    				<label for ="private-button">Privata</label>
    			</div>
    			
    			<input type="hidden" name="track_id" id="track_id">
    			<div>
    				<button class="button" type="submit" >Aggiungi</button>
    				<button class="close-button" type="button" onclick="showHide($('.playlist-form'))">Annulla</button>
    			</div>
    		</div>
    	</fieldset>
    	
  </form>
</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/playlist.js" ></script>
