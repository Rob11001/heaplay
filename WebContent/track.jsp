<%@page import="com.heaplay.model.beans.PurchasableTrackBean"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<div>
	
	<% 
	TrackBean track = (TrackBean)request.getAttribute("currentTrack"); 
	if(track != null ) {
																		%>
		<%@ include file="/_player.jsp"%>
		
	<%} %>
	<% 
	UserBean user = (UserBean) session.getAttribute("user");
	if(user != null) {%>
		<%if(user.getId() != track.getAuthor()) {%>		
			<div>
				<%if(track.getType().equals("pagamento")) { 
					PurchasableTrackBean pTrack = (PurchasableTrackBean) track;
				%>
					<span class="price">Prezzo: <%=pTrack.getPrice()%> </span>
				<%} else {%>
					<span> Free </span>
				<%} %>
				<br>
				<button onclick="addToCart(this)">Aggiungi al carrello</button>
			</div>
			<br>
		<%} %>
		<div>
			<textarea rows="2" cols="20" maxlength="255" placeholder="Scrivi un commento"></textarea>
			<button onclick="uploadComment(this)">Invia</button>
		</div>
	<%} else { %>
		<p>Link per loggare oppure registrarsi per lasciare un commento</p>
	<%} %>
	
	<div class="comment-container">
	</div>
	
</div>

<script src="${pageContext.servletContext.contextPath}/js/song.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/users.js" ></script>
<script src="${pageContext.servletContext.contextPath}/js/comment.js" ></script>
