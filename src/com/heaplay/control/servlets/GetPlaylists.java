package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PlaylistDao;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/getPlaylists")
public class GetPlaylists extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String autocomplete = request.getParameter("autocomplete");
		String track_id = request.getParameter("track_id");
		
		if(id == null && user == null)
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			id = (id !=  null) ? id : user.getId()+"";
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			PlaylistDao playlistDao =  new PlaylistDao(pool);
			ArrayList<PlaylistBean> list = new ArrayList<PlaylistBean>();
			try {
				list = (ArrayList<PlaylistBean>) playlistDao.getPlaylistByAuthor(Long.parseLong(id));
			} catch (NumberFormatException e) {	
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			if(autocomplete != null && track_id != null) {
				try {
					TrackDao trackDao = new TrackDao(pool);
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(track_id);
					TrackBean track = (TrackBean) trackDao.doRetrieveByKey(keys);
					
					ArrayList<PlaylistBean> newList=(ArrayList<PlaylistBean>) list.stream().filter(p ->{
						 boolean bol=p.getName().contains(autocomplete) && !p.getTracks().contains(track);			//Dava alcuni problemi
						 return bol;
					}).collect(Collectors.toList());
					String[] namesOfPlaylist = new String[newList.size()];
					for(int i=0; i < newList.size(); i++)
						namesOfPlaylist[i] = newList.get(i).getName();
					response.setContentType("application/json");
					Gson gson = new Gson();
					String object = gson.toJson(namesOfPlaylist);
					response.getWriter().write(object);
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} else {
				for(int i = 0; i < list.size() ; i++)
					resetBytes(list.get(i).getTracks());
				Gson gson = new Gson();
				String object = gson.toJson(list);
				response.getWriter().write(object);
			}
		}
	}

	
	private static void  resetBytes(List<TrackBean> list) {			
		for(int i=0;i<list.size();i++) {
			TrackBean track = list.get(i);
			track.setImage(null);
			track.setTrack(null);
		}
	}
	
}
