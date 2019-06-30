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
	<style type="text/css">
		a#tagButton {
			border:2px solid #4CAF50;
		}
		
		a#tagButton:hover {
			background-color: #4CAF50;
		}
	
		input.tagElement {
			border:2px solid #4CAF50;
		}
		
		input.tagElement:hover {
			background-color: #4CAF50;
		}
	
	</style>
	<%@ include file="_links.jsp"%>
</head>

<body>
	<div class="uploadFile">
		<form action="upload" name="fileUpload" method="POST" enctype="multipart/form-data" onsubmit="return validateUpload(this)" autocomplete="off">
			<fieldset>
				<legend>Carica la tua canzone</legend>
				
				<label for="songName">Nome con cui sarà visualizzata:</label>
				<input type="text" name="songName" id="songName" />		<br/>
				
				<label for="audio">Scegli la canzone:</label>
				<input type="file" name="audio" id="audio" accept="audio/*" required />	<br/>
				
				<label for="image">Scegli l'immagine</label>
				<input type="file" name="image" id="image" accept="image/*" required />	<br/>
				
				<input type="radio" name="purchasable" id="Free" value="Gratis" checked="checked" onclick="ShowAndHide(0)"> 
				<label for="Free">Gratis</label>
				
				<input type="radio" name="purchasable" id="purchasable" value="A pagamento" onclick="ShowAndHide(1)">	
				<label for="purchasable">A pagamento</label>							<br/>
				
				<div id="divPrice" class="hidden">
					<label for="price">Prezzo:</label>
					<input type="number" name="price" id="price" min="0" max="666">
				</div>
				
				<div id="tags">		
  					<label for="autocomplete">Tags: </label>
					<input id="autocomplete" type="text" name="tags" list="tagSuggestions" onkeyup="autocompleteTags(this,$('#tagSuggestions'))"/>
					<datalist id="tagSuggestions"></datalist>
					<a id = "tagButton" onclick="addTag(this)" >Aggiungi</a>	<br>
				</div>
				
				<input type="hidden" name="authorId" value="<%=user.getId()%>">
				<input type="hidden" name="duration" id="duration"> 
				<br/>
				<br/>
				<input type="submit" value="Carica">
				<audio id="audioFake">
				</audio>
	
			</fieldset>
		</form>
	</div>
	
	<!-- Importazione delle librerie js necessarie-->
	<script src="https://code.jquery.com/jquery-3.4.1.js" type="text/javascript"></script>
<!-- 	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>  Senza alcun motivo funziona -->
    <script src="${pageContext.servletContext.contextPath}/js/jquery.autocomplete.js" ></script>
    <script src="${pageContext.servletContext.contextPath}/js/uploadFunction.js" ></script>	<!-- Permette di indicare il path dinamicamente -->
	<script src ="${pageContext.servletContext.contextPath}/js/validate.js"></script>
</body>
</html>