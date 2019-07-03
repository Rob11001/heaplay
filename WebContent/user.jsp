<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean userPage= (UserBean)request.getAttribute("user");
	ArrayList<TrackBean> listOfTracks = (ArrayList<TrackBean>)request.getAttribute("tracks");
	Integer begin = (Integer)request.getAttribute("begin");
	Integer number = (Integer)request.getAttribute("numberOfTracks");
%>


<img id="user-image" src="/heaplay/getImage?id=<%=userPage.getId()%>&extension=...&user=true" onclick = "inputFile($('#image'))"  width="150px">
<span class="userName"><%=userPage.getUsername()%></span>
<%
UserBean currentUser = ((UserBean)session.getAttribute("user"));
if(currentUser != null && userPage.getId() == currentUser.getId()) {%>
	<form action="/heaplay/uploadImage" name="fileUpload" method="POST" enctype="multipart/form-data"  >
		<input id = "image" type="file" name ="image" accept="image/*" class ="hidden">
		<input type="submit" id="srcImg" value="Carica" class="hidden"></input>
	</form>
<%} %>
<nav class="content-nav">
	<a class="trackButton selected" onclick="selection(this,$('.playlistButton'))" href="#">Brani</a>
	<a class="playlistButton" onclick="selection(this,$('.trackButton'))" href="#">Playlist</a>
</nav> 

<div class="user-tracks">
	
	<%if(listOfTracks.size() == 0) { %>
		<p>Non sono presenti brani</p>
	<%} %>
	
	<%for(int i=0;i<listOfTracks.size();i++) {	//Problema al numero massimo di track che posso mantenere in player in una pagina --> Capire come poter passare ad un altra pagina  per vedere le restanti
		TrackBean track = listOfTracks.get(i);	
	%>
		<%@ include file="/_player.jsp"%>
		
		<%} %>
		<br>
		<form action="/heaplay/user/<%=userPage.getUsername()%>" method="POST">
			<input type="hidden" value="<%=begin%>" name="begin">
			<%for( int i= 0; i< number; i+=5) {%>
				<input type="submit" value="<%=i/5+1%>" onclick="beginValue(this)" id="<%=i%>">	
			<%} %>
		</form>
</div>

<div class="user-playlist hidden">
	<h3>Playlist</h3>
</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>