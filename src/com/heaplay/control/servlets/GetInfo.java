package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.dao.PurchasableTrackDao;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/admin/info")
public class GetInfo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
		UserDao userDao = new UserDao(pool);
		TrackDao trackDao = new TrackDao(pool);
		PurchasableTrackDao purchasableTrackDao = new PurchasableTrackDao(pool);
		
		try {
			long numberOfUsers = userDao.getNumbersOfUsers();
			long numberOfTracks = trackDao.getNumberOfTracks();
			ArrayList<TrackBean> listOfViewed = trackDao.getTracksByParameter("plays", 0, 5);
			ArrayList<TrackBean> listOfLiked = trackDao.getTracksByParameter("likes", 0, 5);
			ArrayList<TrackBean> listOfSold = purchasableTrackDao.getMostSoldTracks();
			
			request.setAttribute("iscritti", numberOfUsers);
			request.setAttribute("numeroBrani", numberOfTracks);
			request.setAttribute("mostViewed", listOfViewed);
			request.setAttribute("mostLiked", listOfLiked);
			request.setAttribute("mostSold", listOfSold);
			
			request.setAttribute("jspPath", response.encodeURL("admin/info.jsp"));
			request.setAttribute("pageTitle", "Info");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
			rd.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
 		
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
