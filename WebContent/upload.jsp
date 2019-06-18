<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	if(user == null)
		response.sendRedirect(getServletContext().getContextPath() + "/");
%>
<head>
<meta charset="UTF-8">
<title>Upload your file</title>
</head>
<body>
	<div class="uploadFile">
		<form action="upload" name="fileUpload" method="POST" enctype="multipart/form-data">
			<fieldset>
				<legend>Carica la tua canzone</legend>
				
				<label for="songName">Nome con cui sarà visualizzata:</label>
				<input type="text" name="songName" id="songName" required/>		<br/>
				
				<label for="audio">Scegli la canzone:</label>
				<input type="file" name="audio" id="audio" accept="audio/*" required />	<br/>
				
				<label for="image">Scegli l'immagine</label>
				<input type="file" name="image" id="image" accept="image/*" required />	<br/>
				
				<label for="Free">Gratis</label>
				<input type="radio" name="purchasable" id="Free" value="Gratis">				<br/>
				<label for="purchasable">A pagamento</label>
				<input type="radio" name="purchasable" id="purchasable" value="A pagamento">	<br/>
				
				<input type="hidden" name="authorId" value="<%=user.getId()%>">
				<br/>
				<br/>
				<input type="submit" value="Carica">
			
			</fieldset>
		
		
		</form>
	
	</div>
</body>
</html>