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
import com.heaplay.model.beans.PurchasableTrackBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.dao.PurchasableTrackDao;
import com.heaplay.model.dao.TrackDao;


@WebServlet("/track")
public class Track extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
		String userName = (String) request.getAttribute("userName");
		String trackName = (String) request.getAttribute("trackName");
		
		if(id == null || userName == null || trackName == null)
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			TrackDao trackDao = new TrackDao(pool);
			ArrayList<String> keys = new ArrayList<String>();
			keys.add(id);
			TrackBean track = null;
			try {
				track = (TrackBean) trackDao.doRetrieveByKey(keys);
				if(track.getType().equals("pagamento")) {
					PurchasableTrackDao purchasableTrackDao = new PurchasableTrackDao(pool);
					track = (TrackBean) purchasableTrackDao.doRetrieveByKey(keys); 
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(track == null || !track.getAuthorName().equals(userName) || !track.getName().replaceAll("\\s","").equals(trackName))
				//Bisognerebbe mandare alla pagina 404
				response.sendRedirect(getServletContext().getContextPath()+"/home");
			else {
				request.setAttribute("currentTrack",track);
				request.setAttribute("jspPath", "/track.jsp");
				request.setAttribute("pageTitle", track.getName());
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
				rd.forward(request, response);
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
