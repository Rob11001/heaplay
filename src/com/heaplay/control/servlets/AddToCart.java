package com.heaplay.control.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.beans.UserBean;

@WebServlet("/addToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String track_id = request.getParameter("track_id");
    	UserBean user = (UserBean) request.getSession().getAttribute("user");
    	
    	if(track_id == null || user == null) 
    		response.sendRedirect(getServletContext().getContextPath()+"/home");
    	else {
    		// Necessita di CartDao vedere come implementare
    	}
    		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
