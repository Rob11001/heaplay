<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	if(user == null)
		response.sendRedirect(getServletContext().getContextPath() + "/home");
%>
<head>
<meta charset="UTF-8">
<title>Upload your file</title>
<link href = "https://code.jquery.com/ui/1.10.4/themes/ui-lightness/jquery-ui.css" rel = "stylesheet"> <!-- Link di stile css necessario per l'autocomplete -->
</head>

<body>
	<div class="uploadFile">
		<form action="upload" name="fileUpload" method="POST" enctype="multipart/form-data" >
			<fieldset>
				<legend>Carica la tua canzone</legend>
				
				<label for="songName">Nome con cui sarà visualizzata:</label>
				<input type="text" name="songName" id="songName" required/>		<br/>
				
				<label for="audio">Scegli la canzone:</label>
				<input type="file" name="audio" id="audio" accept="audio/*" required />	<br/>
				
				<label for="image">Scegli l'immagine</label>
				<input type="file" name="image" id="image" accept="image/*" required />	<br/>
				
				<label for="Free">Gratis</label>
				<input type="radio" name="purchasable" id="Free" value="Gratis" checked="checked" onclick="ShowAndHide(0)"> 
				<label for="purchasable">A pagamento</label>
				<input type="radio" name="purchasable" id="purchasable" value="A pagamento" onclick="ShowAndHide(1)">	<br/>
				
				<div id="divPrice" style="display:none">
					<label for="price">Prezzo:</label>
					<input type="number" name="price" id="price" min="0" max="666">
				</div>
				
				<div>		
  					<label for="tags">Tags: </label>
  					<input id="tags">
				</div>
				
				
				<input type="hidden" name="authorId" value="<%=user.getId()%>">
				<br/>
				<br/>
				<input type="submit" value="Carica">
	
			</fieldset>
		</form>
	</div>
	
	<!-- Importazione delle librerie js necessarie-->
	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script> 
    <script src = "https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
	<script src="${pageContext.servletContext.contextPath}/js/uploadFunction.js" ></script>	<!-- Permette di indicare il path dinamicamente -->
	<!--<script src="https://code.jquery.com/jquery-3.4.1.js" type="text/javascript"></script> Non capisco perchè tale libreria jquery non funzioni-->
</body>
</html>