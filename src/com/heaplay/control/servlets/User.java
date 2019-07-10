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

@WebServlet("/filter/author")
public class User extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		String user = (String) request.getAttribute("userName");
    	UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		Integer begin = request.getParameter("begin") == null ? 0 : Integer.parseInt(request.getParameter("begin"));
    	String owned = request.getParameter("track");
		UserBean currentUser = null;
		ArrayList<TrackBean> listOfTracks = null;
		StringBuffer requestURL = (StringBuffer) request.getAttribute("requestURL");
		int numberOfTracks = 0;
		
		try {
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			UserDao userDao = new UserDao(pool);
			TrackDao trackDao = new TrackDao(pool);
			OwnedTrackDao ownedTrackDao = new OwnedTrackDao(pool);
			boolean flagError = false;
			
			currentUser = userDao.doRetrieveByName(user);
			if(currentUser != null && currentUser.isActive() && !currentUser.getAuth().equals("admin")) {	//Controllo se l'utente esiste ed è attivo
				if(owned == null) {
					//Tracks caricate
					listOfTracks = trackDao.getTracksByAuthor(currentUser.getId(),begin,9,"");
					numberOfTracks = trackDao.getNumberOfTracksOfAuthor(currentUser.getId());
				} else if(userBean != null && userBean.getId() == currentUser.getId()){
					//Track acquistate
					listOfTracks = (ArrayList<TrackBean>) ownedTrackDao.getOwnedTrackByUser(currentUser.getId(),begin,5);
					numberOfTracks = ownedTrackDao.getNumberOfTrackByUser(currentUser.getId());
					request.setAttribute("owned", owned);	//Flag
				} else {
					HttpServletResponse resp = (HttpServletResponse) response;
					resp.sendError(HttpServletResponse.SC_NOT_FOUND);
					request.setAttribute("error_title", "Pagina non trovata - 404");
					request.setAttribute("error", "La pagina \""+ ((HttpServletRequest)request).getRequestURL() + "\" non è stata trovata o non esiste");
					flagError = true;
				}
				
				if(!flagError) {
					request.setAttribute("user", currentUser);
		    		request.setAttribute("tracks", listOfTracks);
		    		request.setAttribute("begin", begin);
		    		request.setAttribute("numberOfTracks",numberOfTracks);
		    		request.setAttribute("jspPath", response.encodeRedirectURL("/user.jsp"));
					request.setAttribute("pageTitle", user);
					
					RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeRedirectURL("/_blank.jsp"));
					rd.forward(request, response);
				}
			}else {
				//Pagina di errore
				request.setAttribute("error_title", "Pagina non trovata - 404");
				request.setAttribute("error", "La pagina \""+ requestURL + "\" non è stata trovata o non esiste");
				response.sendError(response.SC_NOT_FOUND);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			response.sendError(response.SC_INTERNAL_SERVER_ERROR);
		}
	}
}
