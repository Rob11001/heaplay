<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean currentUser = ((UserBean)session.getAttribute("user"));
	ArrayList<TrackBean> listOfTracks = (ArrayList<TrackBean>)request.getAttribute("tracks");
	Integer begin = (Integer)request.getAttribute("begin");
	Integer number = (Integer)request.getAttribute("numberOfTracks");
%>


<img id="user-image" src="/heaplay/getImage?id=<%=currentUser.getId()%>&extension=...&user=true" onclick = "inputFile($('#image'))"  width="150px">
<span class="userName"><%=currentUser.getUsername()%></span>

<form action="/heaplay/uploadImage" name="fileUpload" method="POST" enctype="multipart/form-data"  >
	<input id = "image" type="file" name ="image" accept="image/*" class ="hidden">
	<input type="submit" id="srcImg" value="Carica" class="hidden"></input>
</form>

<nav class="content-nav">
	<a class="trackButton selected" onclick="selection(this,$('.ownedTrackButton'))" href="#">Caricati</a>
	<a class="ownedTrackButton" onclick="selection(this,$('.trackButton'))" href="#">Acquistati</a>
</nav> 

<div class="user-tracks">
	
</div>

<div class="user-ownedtracks hidden">

</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>