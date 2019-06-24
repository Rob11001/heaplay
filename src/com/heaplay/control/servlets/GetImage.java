package com.heaplay.control.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/getImage")
public class GetImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String id = request.getParameter("id");
    	String ext = request.getParameter("extension");
    	
    	if( id == null || ext == null) 												//Controllo probabilmente necessario
    		response.sendRedirect(getServletContext().getContextPath()+"/home");
    	else {
    		ext = ext.substring(ext.indexOf('.'), ext.length());
    		response.setContentType("image/"+ext);
    		
    		TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
    		byte[] imageBytes = null;
    		
    		try {
				imageBytes = trackDao.getImage(Long.parseLong(id));
			} catch (SQLException | NumberFormatException e) {
				e.printStackTrace();
			}
    		
    		OutputStream out = response.getOutputStream();
    		if(imageBytes == null ) {
    			//Prendiamo una immagine di default e la mandiamo
    		} else {
    			out.write(imageBytes);
    		}
    	}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
