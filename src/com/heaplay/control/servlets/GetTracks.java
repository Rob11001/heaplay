package com.heaplay.control.servlets;

import java.io.IOException;
import java.lang.reflect.Array;
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

@WebServlet("/getTracks")
public class GetTracks extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	 }

	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
		Gson gson = new Gson();
		response.setContentType("application/json");
		try {
			List<Bean> list = trackDao.doRetrieveAll(null);
			ArrayList<TrackBean> listOfTrackBean = new ArrayList<TrackBean>();
			for(Bean bean : list)
				listOfTrackBean.add((TrackBean)bean);
			String object = gson.toJson(listOfTrackBean);
			System.out.println("Hey");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		 
	 }

}
