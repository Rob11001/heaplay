package com.heaplay.control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.beans.UserBean;

@WebServlet("/library")
public class GetLibrary extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean userBean = (UserBean) request.getSession().getAttribute("user");
		if(userBean == null)
			response.sendRedirect(getServletContext().getContextPath()+"/login");
		else {
			//Vedere cosa fare
			request.setAttribute("jspPath", "/library.jsp");
			request.setAttribute("pageTitle", "Libreria");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
