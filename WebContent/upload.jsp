<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	if(user == null)
		response.sendRedirect(getServletContext().getContextPath() + "/home");
%>
<h2>Carica</h2>
<hr class="hr-form">
<form action="upload" name="fileUpload" method="POST" enctype="multipart/form-data" onsubmit="return validateUpload(this)" autocomplete="off">
				
	<label for="songName">Nome del Brano<br/></label>
	<input class="form-input-text" type="text" name="songName" id="songName" required/>		<br/>
	
	<label for="audio">Carica il Brano<br/></label>
	<input class="form-input-file" type="file" name="audio" id="audio" accept="audio/*" required />	<br/>
	
	<label for="image">Carica l'Immagine<br/></label>
	<input class="form-input-file" type="file" name="image" id="image" accept="image/*" required /><br/>
	
	<div class="hidden">
		<img id="preview" alt="" src="" width="50px">	<br>
	</div>
	
	<div class="radio-buttons">
		<input class="form-input-radio" type="radio" name="purchasable" id="Free" value="Gratis" checked="checked" onclick="ShowAndHide(0)"> 
		<label for="Free">Gratis </label>
		
		<input class="form-input-radio" type="radio" name="purchasable" id="purchasable" value="A pagamento" onclick="ShowAndHide(1)">	
		<label for="purchasable">A pagamento</label><br/>
	</div>
	<div id="divPrice" class="hidden">
		<label for="price">Prezzo<br/></label>
		<input class="form-input-text" type="number" name="price" id="price" min="0" max="666">
	</div>
		
	<label for="tags">Tag<br/></label>
	
	<input class="form-input-text" id="autocomplete" type="text" name="tags" list="tagSuggestions" onkeyup="autocompleteTags(this,$('#tagSuggestions'))"/>
	<datalist id="tagSuggestions"></datalist>
	
	<button class="form-input-submit" id="tagButton" onclick="addTag(this)">Aggiungi</button><br>
	<div class="added-tags">
	</div>
		
	<input type="hidden" name="authorId" value="<%=user.getId()%>">
	<input type="hidden" name="duration" id="duration"> 
	
	<input class="form-input-submit" type="submit" value="Carica">
	<audio id="audioFake">
	</audio>
</form>
	
<script src="${pageContext.servletContext.contextPath}/js/uploadFunction.js" ></script>	<!-- Permette di indicare il path dinamicamente -->
<script src ="${pageContext.servletContext.contextPath}/js/validate.js"></script>