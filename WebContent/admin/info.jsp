<%@page import="com.heaplay.model.beans.PurchasableTrackBean"%>
<%@page import="com.heaplay.model.beans.TrackBean"%>
<%@page import="java.util.ArrayList"%>
<%
	long iscritti = (Long)request.getAttribute("iscritti");
	long numeroBrani = (Long)request.getAttribute("numeroBrani");
	ArrayList<TrackBean> listOfViewed = (ArrayList<TrackBean>)request.getAttribute("mostViewed");
	ArrayList<TrackBean> listOfLiked = (ArrayList<TrackBean>)request.getAttribute("mostLiked");
	ArrayList<TrackBean> listOfSold = (ArrayList<TrackBean>)request.getAttribute("mostSold");
%>

<div>
	<h4>Numero iscritti : <%=iscritti%></h4>
	<br>
	<h4>Numero brani caricati : <%=numeroBrani%></h4>
	<br>

	<nav class="content-nav">
		<a class="mostViewedButton selected" onclick="selectOption($('.mostViewedSongs'),this)" href="#">Più Ascoltati</a>
		<a class="mostLikedButton" onclick="selectOption($('.mostLikedSongs'),this)" href="#">Più votati</a>
		<a class="mostSoldButton" onclick="selectOption($('.mostSoldSongs'),this)" href="#">Più venduti</a>
	</nav> 

	<div class="mostViewedSongs only">
		<table>
			<tr>
				<th>Brano</th>
				<th>Autore</th>
				<th>Ascolti</th>
			</tr>
			<%for(int i=0 ; i < listOfViewed.size() ; i++) {
				TrackBean bean = listOfViewed.get(i);
			%>
			<tr>
				<td><span class='song-name'><a
						href='/heaplay/user/<%=bean.getAuthorName()%>/<%=bean.getName().replaceAll("\\s","")%>?id=<%=bean.getId()%>'><%=bean.getName()%></a></span></td>
				<td><span class='author'><a
						href='/heaplay/user/<%=bean.getAuthorName()%>'><%=bean.getAuthorName()%></a></span></td>
				<td><span><%=bean.getPlays()%></span></td>
			</tr>
			<%} %>
		</table>
	</div>
	
	<div class="mostLikedSongs hidden"> 
		<table>
			<tr>
				<th>Brano</th>
				<th>Autore</th>
				<th>Like</th>
			</tr>
			<%for(int i=0 ; i < listOfLiked.size() ; i++) {
				TrackBean bean = listOfLiked.get(i);
			%>
			<tr>
				<td><span class='song-name'><a
						href='/heaplay/user/<%=bean.getAuthorName()%>/<%=bean.getName().replaceAll("\\s","")%>?id=<%=bean.getId()%>'><%=bean.getName()%></a></span></td>
				<td><span class='author'><a
						href='/heaplay/user/<%=bean.getAuthorName()%>'><%=bean.getAuthorName()%></a></span></td>
				<td><span><%=bean.getLikes()%></span></td>
			</tr>
			<%} %>
		</table>
	</div>
	
	<div class="mostSoldSongs hidden">
		<table>
			<tr>
				<th>Brano</th>
				<th>Autore</th>
				<th>Vendute</th>
			</tr>
			<%for(int i=0 ; i < listOfSold.size() ; i++) {
				TrackBean bean = listOfSold.get(i);
			%>
			<tr>
				<td><span class='song-name'><a
						href='/heaplay/user/<%=bean.getAuthorName()%>/<%=bean.getName().replaceAll("\\s","")%>?id=<%=bean.getId()%>'><%=bean.getName()%></a></span></td>
				<td><span class='author'><a
						href='/heaplay/user/<%=bean.getAuthorName()%>'><%=bean.getAuthorName()%></a></span></td>
				<td><span><%=((PurchasableTrackBean)bean).getSold()%></span></td>
			</tr>
			<%} %>
		</table>
	</div>

</div>