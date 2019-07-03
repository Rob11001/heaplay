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

@WebServlet("/user/*")
public class RewriteUrl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//Lettura URL e parametri
    	String URI = request.getRequestURI();
    	String[] params = URI.split("/");
    	
    	if(params.length == 5) {
    		request.setAttribute("userName", params[params.length-2]);
    		request.setAttribute("trackName", params[params.length-1]);
    		RequestDispatcher rd = getServletContext().getRequestDispatcher("/track");
    		rd.forward(request, response);
    	} else {			
	    	String user = params[params.length-1];
	    	UserBean userBean =((UserBean)request.getSession().getAttribute("user"));
	    	Integer begin = request.getParameter("begin") == null ? 0 : Integer.parseInt(request.getParameter("begin"));
	    	String userName = (userBean != null) ? userBean.getUsername() : null;
	    	
	    	
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
