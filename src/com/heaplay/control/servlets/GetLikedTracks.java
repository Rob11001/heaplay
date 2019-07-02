package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/getLikedTracks")
public class GetLikedTracks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		response.setContentType("application/JSON");
 		ArrayList<Bean> list = new ArrayList<Bean>();
 		TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
 		try {
			list = (ArrayList<Bean>) trackDao.doRetrieveAll((a,b) -> new Long(((TrackBean)a).getLikes() - ((TrackBean)b).getLikes()).intValue());
			ArrayList<Bean> listOfObjects = new ArrayList<Bean>();
			listOfObjects.addAll(list.subList(0, 5));
			resetBytes(listOfObjects);
			Gson gson = new Gson();
			String objectJson = gson.toJson(listOfObjects);
			response.getWriter().write(objectJson);
 		} catch (SQLException e) {
			e.printStackTrace();
		}
 	}

 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
 	
	private static void  resetBytes(List<Bean> array) {			
		for(int i=0;i<array.size();i++) {
			TrackBean track = (TrackBean) array.get(i);
			track.setImage(null);
			track.setTrack(null);
		}
	}
}
