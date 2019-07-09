<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean userPage= (UserBean)request.getAttribute("user");
	ArrayList<TrackBean> listOfTracks = (ArrayList<TrackBean>)request.getAttribute("tracks");
	Integer begin = (Integer)request.getAttribute("begin");
	Integer number = (Integer)request.getAttribute("numberOfTracks");
	UserBean currentUser = ((UserBean)session.getAttribute("user"));

%>

<div class="page-header">
	<div class="relative-container">
		<img class="page-image" src="/heaplay/getImage?id=<%=userPage.getId()%>&user=true" onclick = "inputFile($('#image'))">
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
	<a class="trackButton selected" onclick="selection(this,$('.playlistButton'),$('.user-playlist'),$('.user-tracks'))" href="#">Brani Caricati</a>
	<a class="playlistButton" onclick="selection(this,$('.trackButton'),$('.user-tracks'),$('.user-playlist')),getPlaylist($('.user-playlist'))" href="#">Playlist</a>
</nav> 

<div class="user-tracks">
	
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
	<form action="<%=response.encodeURL("/heaplay/user/"+userPage.getUsername()) %>" method="POST">
		<input type="hidden" value="<%=begin%>" name="begin" id="currentPage"> 
		<%for( int i= 0; i< number; i+=5) {%>
			<input type="submit" value="<%=i/5+1%>" onclick="beginValue(this)" id="<%=i%>">	
		<%} %>
	</form>
</div>

<div class="user-playlist hidden" >
	<h3>Playlist</h3>
</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>