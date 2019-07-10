package com.heaplay.control.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")	//Cambiato il mapping per problemi con / nel link dei file esterni
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Cache-Control", "private,no-store,no-cache");
		request.setAttribute("jspPath", response.encodeURL("/home.jsp"));
		request.setAttribute("pageTitle", "Homepage");
		RequestDispatcher rs = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
		rs.forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
