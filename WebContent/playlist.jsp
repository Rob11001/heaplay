<%@page import="com.heaplay.model.beans.PlaylistBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
UserBean currentUser = ((UserBean) session.getAttribute("user"));
UserBean userPage = (UserBean) request.getAttribute("userPage");
PlaylistBean playlist = (PlaylistBean) request.getAttribute("playlist");
ArrayList<TrackBean>  listOfTracks = (ArrayList<TrackBean>) playlist.getTracks();
Integer number = (Integer) request.getAttribute("number");
Integer begin = (Integer) request.getAttribute("begin");

%>

<div class="page-header">
	<img class="page-image" src="/heaplay/getImage?id=<%=listOfTracks.get(0).getId()%>&extension=<%=listOfTracks.get(0).getImageExt()%>" onclick = "inputFile($('#image'))"  width="150px">
	<div>
		<span class="page-name"><%=playlist.getName()%></span>
		<span class="page-subname"><a href="<%=response.encodeURL("/heaplay/user/"+playlist.getAuthorName())%>"><%=playlist.getAuthorName()%></a></span>
	</div>
</div>

<!-- Settare una grandezza migliore per le frecce -->
<a onclick="prev()"><i class="fas fa-chevron-circle-left"></i></a><!--
 --><a onclick="next()"><i class="fas fa-chevron-circle-right"></i></a>


<div class="user-tracks">
	<%if(listOfTracks.size() == 0) { %>
		<p>Non sono presenti brani</p>
	<%} %>
	
	<%for(int i=0;i<listOfTracks.size();i++) {	
		TrackBean track = listOfTracks.get(i);	
	%>
		<div class="item">
			<%@ include file="/_player.jsp"%>
			<%if(currentUser != null && currentUser.getId() == userPage.getId()) {%>
				<button class="item-remove" onclick="removeFromPlaylist(this)">Rimuovi</button>
			<%} %>
		</div>
	<%} %>
	<br>

<form class="pages-buttons" action="<%=response.encodeURL("/heaplay/user/"+userPage.getUsername()+"/playlist/"+playlist.getName()+"?id="+playlist.getId()) %>" method="POST"> 
		<input type="hidden" value="<%=begin%>" name="begin" id="currentPage">
		<%for( int i= 0; i< number; i+=10) {%>
			<button type="submit" onclick="beginValue(this)" id="<%=i%>"><%=i/5+1%></button>
		<%} %>
	</form>
</div>


<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/playlist.js" ></script>
