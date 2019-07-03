<%@page import="com.heaplay.model.beans.UserBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	if(user == null)
		response.sendRedirect(getServletContext().getContextPath() + "/home");
%>
	<div class="uploadFile">
		<form action="upload" name="fileUpload" method="POST" enctype="multipart/form-data" onsubmit="return validateUpload(this)" autocomplete="off">
						
			<label for="songName">Nome del Brano<br/></label>
			<input class="form-input-text" type="text" name="songName" id="songName" required/>		<br/>
			
			<label for="audio">Scegli la canzone:</label>
			<input type="file" name="audio" id="audio" accept="audio/*" required />	<br/>
			
			<label for="image">Scegli l'immagine</label>
			<input type="file" name="image" id="image" accept="image/*" required />	
			<br/>
			<div class="hidden">
				<img id = "preview" alt="" src="" width="50px">	<br>
			</div>
			
			<input type="radio" name="purchasable" id="Free" value="Gratis" checked="checked" onclick="ShowAndHide(0)"> 
			<label for="Free">Gratis</label>
			
			<input type="radio" name="purchasable" id="purchasable" value="A pagamento" onclick="ShowAndHide(1)">	
			<label for="purchasable">A pagamento</label>							<br/>
			
			<div id="divPrice" class="hidden">
				<label for="price">Prezzo:</label>
				<input class="form-input-text" type="number" name="price" id="price" min="0" max="666">
			</div>
			
			<div id="tags">		
 					<label for="autocomplete">Tags: </label>
				<input class="form-input-text" id="autocomplete" type="text" name="tags" list="tagSuggestions" onkeyup="autocompleteTags(this,$('#tagSuggestions'))"/>
				<datalist id="tagSuggestions"></datalist>
				<a id="tagButton" onclick="addTag(this)">Aggiungi</a>	<br>
			</div>
			
			<input type="hidden" name="authorId" value="<%=user.getId()%>">
			<input type="hidden" name="duration" id="duration"> 
			<br/>
			<br/>
			<input class="form-input-submit" type="submit" value="Carica">
			<audio id="audioFake">
			</audio>
		</form>
	</div>
	
	<!-- Importazione delle librerie js necessarie-->
	<script src="https://code.jquery.com/jquery-3.4.1.js" type="text/javascript"></script>
<!-- 	<script src = "https://code.jquery.com/jquery-1.10.2.js"></script>  Senza alcun motivo funziona -->
    <script src="${pageContext.servletContext.contextPath}/js/uploadFunction.js" ></script>	<!-- Permette di indicare il path dinamicamente -->
	<script src ="${pageContext.servletContext.contextPath}/js/validate.js"></script>
</body>
</html>