<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	if(user == null)
		response.sendRedirect(getServletContext().getContextPath() + "/home");
%>
	<div class="uploadFile">
		<form action="upload" name="fileUpload" method="POST" enctype="multipart/form-data" >
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
				
				<div id="divPrice" style="display:none">
					<label for="price">Prezzo:</label>
					<input type="number" name="price" id="price" min="0" max="666">
				</div>
				
				<div>		
  					<label for="autocomplete">Tags: </label>
					<input type="text" name="tags" id="autocomplete" autocomplete="off"/>
				</div>
				
				
				<input type="hidden" name="authorId" value="<%=user.getId()%>">
				<br/>
				<br/>
				<input type="submit" value="Carica">
				<audio id="audioFake">
				</audio>
				<span id="duration">Hey</span>
	
			</fieldset>
		</form>
	</div>
	
	<!-- Importazione delle librerie js necessarie-->
    <script src="${pageContext.servletContext.contextPath}/js/uploadFunction.js" ></script>	<!-- Permette di indicare il path dinamicamente -->
	<script src="${pageContext.servletContext.contextPath}/js/jquery.autocomplete.js" ></script>
	<!-- <script src="https://code.jquery.com/jquery-3.4.1.js" type="text/javascript"></script>  --> <!-- Non funziona senza alcun motivo apparente -->
</body>
</html>