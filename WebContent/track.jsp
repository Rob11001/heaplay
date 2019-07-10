<%@page import="com.heaplay.model.beans.Cart"%>
<%@page import="com.heaplay.model.beans.PurchasableTrackBean"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<div>
	
	<% 
	TrackBean track = (TrackBean)request.getAttribute("currentTrack"); 
	Cart<TrackBean> cart = (Cart<TrackBean>)session.getAttribute("cart");
	String owned = (String)request.getAttribute("owned");
	if(track != null ) {
																		%>
		<%@ include file="/_player.jsp"%>
		
	<%} %>
	<% 
	UserBean user = (UserBean) session.getAttribute("user");
	if(user != null) {
		if(user.getId() != track.getAuthor()) {
			if(user.getAuth().equals("admin")) { %>		
				<form action="/heaplay/removeTrack" method="POST">
					<input type="hidden" name="track_id" value="<%=track.getId()%>">
					<input type="submit" value="Blocca">
				</form>	
			<% } else {%>
			<div>
				<%if(track.getType().equals("pagamento")) { 
					PurchasableTrackBean pTrack = (PurchasableTrackBean) track;
				%>
					<span class="price">Prezzo: <%=pTrack.getPrice()%> </span>
				<%} else {%>
					<span> Gratuita </span>
				<%} %>
				<br>
				<%if(!cart.getItems().contains(track) && owned.equals("false")) { %>
					<button onclick="addToCart(this)">Aggiungi al carrello</button>
				<%} else if(owned.equals("false")){%>
					<span>Già aggiunta al carrello</span>
				<%} else {%>
					<span>Acquistata</span>
				<%} %>
			</div>
			<% } %>
			<br>
		<%} %>
	<%} else { %>
		<p>Link per loggare oppure registrarsi per lasciare un commento</p>
	<%} %>
	<h3>Commenti</h3>
	<hr class="hr-form">
	<div class="write-comment">
			<textarea class="form-input-textarea" maxlength="255" placeholder="Scrivi un commento"></textarea>
			<button onclick="uploadComment(this)">Invia</button>
	</div>
	<div class="comment-container">
	</div>
	
</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/comment.js" ></script>
