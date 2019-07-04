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
    	} else if(params.length == 6){
    		request.setAttribute("userName", params[params.length-3]);
    		request.setAttribute("playlistName", params[params.length-1]);
    		RequestDispatcher rd = getServletContext().getRequestDispatcher("/playlist");
    		rd.forward(request, response);
    	} else {
	    	request.setAttribute("userName", params[params.length-1]);
	    	RequestDispatcher rd = getServletContext().getRequestDispatcher("/author");
    		rd.forward(request, response);
    	}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
