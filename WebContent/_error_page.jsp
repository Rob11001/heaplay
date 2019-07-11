<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<%
	String error_title = (String)request.getAttribute("error_title");
	String error = (String)request.getAttribute("error");
	
	//Per gestire anche le eccezioni
	String title = error_title != null ? error_title : "Errore";
	error = error != null ? error : exception.getMessage();
%>


<html>
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<title><%=title %></title>

<%-- <%@ include file="/_links.jsp"%> Sistemare lo stile per la pagina di errore--%>
</head>
<body>

	<div class="error">
		<!-- Trovare immagine da caricare -->
		<h3><span><%=error %></span></h3>
		
	</div>

</body>
</html>