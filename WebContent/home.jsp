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
	
<div class="loading hidden">
	<img alt="Loading..." src="/heaplay/images/loading.gif" width="50px">
</div>

<div id="content">			
</div>
<script src="https://code.jquery.com/jquery-3.4.1.js" type="text/javascript"></script>
<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/load.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
	