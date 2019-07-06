<%@page import="com.heaplay.model.beans.PlaylistBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
UserBean currentUser = ((UserBean) session.getAttribute("user"));
UserBean userPage = (UserBean) request.getAttribute("userPage");
PlaylistBean playlist = (PlaylistBean) request.getAttribute("playlist");
ArrayList<TrackBean>  listOfTracks = (ArrayList<TrackBean>) playlist.getTracks();
Integer number = (Integer) request.getAttribute("number");
Integer begin = (Integer) request.getAttribute("begin");

%>

<div id = "playlist-page">
	<h3>Playlist</h3>
	<p>Qui mettiamo un logo e uno sfondo con nome dell'autore della playlist</p>
	<!-- Settare una grandezza migliore per le frecce -->
	<a onclick="prev()"><i class="fas fa-chevron-circle-left"></i></a> <a onclick="next()"><i class="fas fa-chevron-circle-right"></i></a>
</div>

<div class="user-tracks">
	<%if(listOfTracks.size() == 0) { %>
		<p>Non sono presenti brani</p>
	<%} %>
	
	<%for(int i=0;i<listOfTracks.size();i++) {	
		TrackBean track = listOfTracks.get(i);	
	%>
		<div>
			<%@ include file="/_player.jsp"%>
			<%if(currentUser != null && currentUser.getId() == userPage.getId()) {%>
				<button onclick="removeFromPlaylist(this)">Rimuovi</button>
			<%} %>
		</div>
	<%} %>
	<br>

<form action="/heaplay/user/<%=userPage.getUsername()%>/playlist/<%=playlist.getName()%>?id=<%=playlist.getId()%>" method="POST"> 
		<input type="hidden" value="<%=begin%>" name="begin" id="currentPage">
		<%for( int i= 0; i< number; i+=10) {%>
			<input type="submit" value="<%=i/5+1%>" onclick="beginValue(this)" id="<%=i%>">	
		<%} %>
	</form>
</div>


<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/playlist.js" ></script>
