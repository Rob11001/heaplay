package com.heaplay.control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.heaplay.model.beans.UserBean;

@WebServlet("/register")
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		if(userBean != null)
			response.sendRedirect("/");
		else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/register.jsp");
			rd.forward(request, response);
		}
	}

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
	}

}
