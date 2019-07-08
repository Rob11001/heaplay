<%@page import="com.heaplay.model.ConnectionPool"%>
<%@page import="com.heaplay.model.dao.TrackDao"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
%>
<br>
<p>Sei nella HomePage</p>
<br>
<% if(user != null) {%>
	<p>Benvenuto <%=user.getUsername() %></p>
<% }	%>

<!-- L'admin non ha bisogno di vedere le più popolari -->
<%if(user == null || !user.getAuth().equals("admin")){%>
	<div id="playlists">
		<h3>Playlist più ascoltate</h3>
		<div class="flex-container">
	
		</div>
	</div>

	<div id="songs">
		<h3>Canzoni più ascoltate</h3>
		<div class="flex-container">
	
		</div>
	</div>
<% } %>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/home.js" ></script>