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

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/view")
public class View extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Lettura parametri
		String id = request.getParameter("id");
		String like = request.getParameter("like");
				
		if(id == null)
			response.sendRedirect(response.encodeURL(getServletContext().getContextPath()+"/home"));
		else {
			ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
			TrackDao trackDao = new TrackDao(pool);
			
			TrackBean bean = null;
			List<String> keys = new ArrayList<String>();
			keys.add(id);
			
			try {
				//Lettura della track, aumento likes o plays e aggiornamento
				bean = (TrackBean) trackDao.doRetrieveByKey(keys);
				if(bean != null) {
					if(like == null)
						bean.setPlays(bean.getPlays()+1);
					else 
						bean.setLikes(bean.getLikes()+1);
					trackDao.doUpdate(bean);
				}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
