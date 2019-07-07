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
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.OwnedTrackDao;
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/library")
public class GetLibrary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		String owned = request.getParameter("track");
		
		if(userBean == null)
			response.sendRedirect(getServletContext().getContextPath()+"/login");
		else {
			//Vedere cosa fare
			Integer begin = request.getParameter("begin") == null ? 0 : Integer.parseInt(request.getParameter("begin"));
	    
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			TrackDao trackDao = new TrackDao(pool);
			ArrayList<TrackBean> listOfTracks = null;
			try {
				int numberOfTracks = 0;
				if(owned == null) {
					listOfTracks = trackDao.getTracksByAuthor(userBean.getId(),begin,5);
					numberOfTracks = trackDao.getNumberOfTracksOfAuthor(userBean.getId());
				} else {
					OwnedTrackDao ownedTrackDao = new OwnedTrackDao(pool);
					listOfTracks = (ArrayList<TrackBean>) ownedTrackDao.getOwnedTrackByUser(userBean.getId(),begin,5);
					numberOfTracks = ownedTrackDao.getNumberOfTrackByUser(userBean.getId());
					request.setAttribute("owned", owned);
				}
		    	request.setAttribute("tracks", listOfTracks);
		    	request.setAttribute("begin", begin);
		    	request.setAttribute("numberOfTracks",numberOfTracks);
		    	request.setAttribute("jspPath", "/library.jsp");
				request.setAttribute("pageTitle", "Libreria");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
				rd.forward(request, response);
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(response.SC_INTERNAL_SERVER_ERROR);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
