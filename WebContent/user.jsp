<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean userPage= (UserBean)request.getAttribute("user");
	ArrayList<TrackBean> listOfTracks = (ArrayList<TrackBean>)request.getAttribute("tracks");
	Integer begin = (Integer)request.getAttribute("begin");
	Integer number = (Integer)request.getAttribute("numberOfTracks");
	UserBean currentUser = ((UserBean)session.getAttribute("user"));
	String owned = (String) request.getAttribute("owned");

%>

<div class="page-header">
	<div class="relative-container">
		<img class="page-image <%if(currentUser == null || currentUser.getId() != userPage.getId()){%>not-user <%}%>" src="/heaplay/getImage?id=<%=userPage.getId()%>&user=true" <%if(currentUser != null && currentUser.getId() == userPage.getId()){%> title="Cambia Immagine" <%} %> onclick = "inputFile($('#image'))">
		<%if(currentUser != null && userPage.getId() == currentUser.getId()) {%>
		<form class="middle-bottom" action="<%=response.encodeURL("/heaplay/uploadImage") %>" name="fileUpload" method="POST" enctype="multipart/form-data">
			<input id="image" type="file" name ="image" accept="image/*" class ="hidden">
			<button type="submit" id="srcImg"class="hidden">Conferma</button>
		</form>
	<%} %>
	</div>
	<span class="page-name"><%=userPage.getUsername()%></span>
	<%if (currentUser != null && currentUser.getAuth().equals("admin")) {%>
		<form action="/heaplay/removeUser" method="POST">
			<input type="hidden" name="user_id" value="<%=userPage.getId()%>">
			<input type="submit" value="Elimina Utente">
		</form>	
	<% } %>
</div>


<nav class="content-nav">
	<a class="trackButton <%= (owned == null) ? "selected" : "" %>" onclick="selectOption($('.user-tracks'),this)" href="<%=response.encodeURL("/heaplay/user/"+userPage.getUsername())%>">Brani Caricati</a>
	<a class="playlistButton" onclick="selectOption($('.user-playlist'),this),getPlaylist($('.user-playlist'))" href="#playlist">Playlist</a>
	<%if(currentUser != null && currentUser.getId() == userPage.getId()) { %>
		<a class="ownedTrackButton <%= (owned != null) ? "selected" : "" %>" onclick="selectOption($('.user-tracks'),this)" href="<%=response.encodeURL("/heaplay/user/"+currentUser.getUsername()+"?track=owned")%>">Brani Acquistati</a>
	<% }%>
</nav> 

<div class="user-tracks only">
	
	<div class="flex-container">
		<%if(listOfTracks.size() == 0) { %>
			<p>Non sono presenti brani</p>
		<%} %>
		
		<%for(int i=0;i<listOfTracks.size();i++) {	//Problema al numero massimo di track che posso mantenere in player in una pagina --> Capire come poter passare ad un altra pagina  per vedere le restanti
			TrackBean track = listOfTracks.get(i);	
		%>
			<%@ include file="/_player.jsp"%>			
		<%} %>
	</div>
	<form class="pages-buttons" action="<%=response.encodeURL("/heaplay/user/"+userPage.getUsername()+((owned!=null) ? "?track=owned" : ""))%>" method="POST">
		<input type="hidden" value="<%=begin%>" name="begin" id="currentPage"> 
		<%for( int i= 0; i< number; i+=9) {%>
			<button type="submit" onclick="beginValue(this)" id="<%=i%>"><%=i/9+1%></button>
		<%} %>
	</form>
</div>

<div class="user-playlist hidden" >
	<h3>Playlist</h3>
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