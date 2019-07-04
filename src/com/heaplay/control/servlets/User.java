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
import com.heaplay.model.dao.TrackDao;
import com.heaplay.model.dao.UserDao;

/**
 * Servlet implementation class User
 */
@WebServlet("/author")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getAttribute("userName");
    	Integer begin = request.getParameter("begin") == null ? 0 : Integer.parseInt(request.getParameter("begin"));
    	
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
		UserDao userDao = new UserDao(pool);
		TrackDao trackDao = new TrackDao(pool);
		UserBean currentUser = null;
		ArrayList<TrackBean> listOfTracks = null;
		try {
			currentUser = userDao.doRetrieveByName(user);
			if(currentUser != null) {
				listOfTracks = trackDao.getTracksByAuthor(currentUser.getId(),begin,5);
				int numberOfTracks = trackDao.getNumberOfTracksOfAuthor(currentUser.getId());
				request.setAttribute("user", currentUser);
	    		request.setAttribute("tracks", listOfTracks);
	    		request.setAttribute("begin", begin);
	    		request.setAttribute("numberOfTracks",numberOfTracks);
	    		request.setAttribute("jspPath", "/user.jsp");
				request.setAttribute("pageTitle", user);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
				rd.forward(request, response);
			}else 
				;//Mandalo alla pagina di errore
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
