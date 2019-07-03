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

<div id="playlists">
	<p>Inseriamo qui le playlist più ascoltate</p>
</div>

<div id="songs">
	<p>Inseriamo qui le canzoni più ascoltate</p>
</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/home.js" ></script>