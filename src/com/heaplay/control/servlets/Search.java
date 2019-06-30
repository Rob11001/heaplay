package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TagBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PlaylistDao;
import com.heaplay.model.dao.TagDao;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/search")
public class Search extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String query = request.getParameter("q");
		String filter = request.getParameter("filter");
		String autocomplete = request.getParameter("auto");
		
		
		if(query != null && !query.equals("") && filter != null && !filter.equals("")) {
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			ArrayList<Bean> list = null;
			try {
				switch(filter) {
				case "user": UserDao userDao = new UserDao(pool);
							list = (ArrayList<Bean>) userDao.doRetrieveAll(null);
							list = filter(query, list);
							break;
				case "track":TrackDao trackDao = new TrackDao(pool);
							list = (ArrayList<Bean>) trackDao.doRetrieveAll(null);
							list = filter(query, list);
							resetBytes(list);
							break;
				case "playlist":PlaylistDao playlistDao = new PlaylistDao(pool);
							list = (ArrayList<Bean>) playlistDao.doRetrieveAll(null);
							list = filter(query, list);
							for(int i=0;i<list.size();i++)
								resetBytes(((ArrayList<TrackBean>)((PlaylistBean)list.get(i)).getTracks()));
							break;
				case "tag":  TagDao tagDao = new TagDao(pool);
							list = (ArrayList<Bean>) tagDao.doRetrieveAll(null);
							list = filter(query, list);
							break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Gson gson = new Gson();
			String objectJson = null;
			if(autocomplete == null)
				objectJson = gson.toJson(list);
			else {
				String[] suggestions = new String[list.size()];
				for(int i=0;i<list.size();i++)
					suggestions[i] = list.get(i).getBeanName() ;
				objectJson = gson.toJson(suggestions);
			}
			response.getWriter().write(objectJson);
		}
		else 
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private ArrayList<Bean> filter(String query,ArrayList<Bean> list) {
		//Potremmo usare una regex
		ArrayList<Bean> newList=(ArrayList<Bean>) list.stream().filter(p ->p.getBeanName().contains(query)).collect(Collectors.toList());
		return newList;
	
	}
	
	@SuppressWarnings("rawtypes")
	private static void  resetBytes(ArrayList list) {			
		for(int i=0;i<list.size();i++) {
			TrackBean track = (TrackBean) list.get(i);
			track.setImage(null);
			track.setTrack(null);
		}
	}

}