package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/removeTrack")
public class RemoveTrack extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String track_id = request.getParameter("track_id");
		
		if(user == null || !user.getAuth().equals("admin") || track_id == null)
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
			ArrayList<String> keys = new ArrayList<String>();
			keys.add(track_id);
			try {
				TrackBean track = (TrackBean) trackDao.doRetrieveByKey(keys); 
				if(track == null) {
					//Fare qualcosa
				} else {
					if(track.getType().equals("pagamento") ) {
						track.setIndexable(false);
						trackDao.doUpdate(track);
					} else {
						trackDao.doDelete(keys);
					}
					response.sendRedirect(getServletContext().getContextPath()+"/home");
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(response.SC_INTERNAL_SERVER_ERROR);
			}
		}
	
	}

}
