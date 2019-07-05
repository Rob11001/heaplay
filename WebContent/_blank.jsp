<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String jspPath = (String) request.getAttribute("jspPath"); //Mancano i controlli
	String pageTitle = (String) request.getAttribute("pageTitle");
	UserBean user = (UserBean) session.getAttribute("user");
%>
<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title><%=pageTitle%></title>

<%@ include file="/_links.jsp"%>
</head>
<body>
	<script src="${pageContext.servletContext.contextPath}/js/jquery-3.4.1.min.js" ></script>
	<script src="${pageContext.servletContext.contextPath}/js/search.js" ></script>
	<script src="${pageContext.servletContext.contextPath}/js/menu.js" ></script>
	
	<jsp:include page="/_header.jsp" />
	
		<div class="content-wrapper">
			<div class="search-content">	
				<div class="loading hidden">
					<img alt="Loading..." src="/heaplay/images/loading.gif" width="50px">
				</div>
				<div id="content" class="user-tracks">		
				</div>
			</div>
		<jsp:include page="<%=jspPath%>"/>
	
	</div>
	
	<jsp:include page="/_footer.jsp" />
	
</body>
</html>