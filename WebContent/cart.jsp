<%@page import="com.heaplay.model.beans.PurchasableTrackBean"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="com.heaplay.model.beans.Cart"%>
<%@page import="com.heaplay.model.beans.UserBean"%>
<%
	UserBean user = (UserBean)session.getAttribute("user");
	Cart<TrackBean> cart = (Cart<TrackBean>) session.getAttribute("cart");
	double sum = 0;
%>

<div>
	<h3>Carrello</h3>
	<div class="cart">
		<%
		ArrayList<TrackBean> list = (ArrayList<TrackBean>)cart.getItems();
		for(int i = 0 ; i < list.size() ; i++) {	
			TrackBean track = list.get(i);
			sum += track.getType().equals("free") ? 0 : (((PurchasableTrackBean)track).getPrice());
		%>
			<div class="cart-item">
				<div class="song-image">
					<img 
						src="/heaplay/getImage?id=<%=track.getId()%>&extension=<%=track.getImageExt()%>"
					alt="Errore">
				</div>
				<div class="carted-content">
					<span class="author"><a href="<%=response.encodeURL("/heaplay/user/"+track.getAuthorName()+"/"+track.getName().replaceAll("\\s","")+"?id="+track.getId()) %>"><%=track.getAuthorName()%></a></span> <br>
					<span class="song-name"><a href="/heaplay/user/<%=track.getAuthorName()%>/<%=track.getName().replaceAll("\\s","")%>?id=<%=track.getId()%>"><%=track.getName()%></a></span> <br>
					<span class="price">Costo: <%=(track.getType().equals("free")) ? "Gratuita" : (String.format("%.2f",((PurchasableTrackBean)track).getPrice()))%></span>
					<span class="upload-date hidden">Data : <%=track.getUploadDate()%></span><br>
				</div>
				<button class="carted-remove" onclick="removeFromCart(this)">Rimuovi</button>
			</div>
		<%} 
		if(list.size() == 0) { %>
			<p>Il tuo carrello è al momento vuoto</p>
		<%} else {%>
			<br>
			<div class="cart-sum" >
				<span>Costo Totale: <span id="sum"><%=String.format("%.2f",sum) %></span></span> <br>
				<button onclick="purchase()">Acquista</button>
			</div>
		<%} %>
	</div>
	<div class="loading-cart hidden">
		<img alt="Loading..." src="/heaplay/images/loading.gif" width="50px">
	</div>
</div>

<script src="${pageContext.servletContext.contextPath}/js/cart.js" ></script>
