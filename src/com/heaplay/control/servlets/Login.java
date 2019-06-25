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
import javax.servlet.http.HttpSession;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.UserDao;

@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		if(userBean != null)
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
		ArrayList<String> userKeys = new ArrayList<String>();
		String errorMessage = "";
		if(userBean != null)													//Controllo esistenza UserBean
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			String email = request.getParameter("email");						//Controllo parametri
			
			if(email != null && !email.trim().equals("")) {
				request.setAttribute("email", email);
				userKeys.add(email);
			} 
			else 
				errorMessage += "Email non inserita";
			
			String password = request.getParameter("password");
			
			if(password != null && !password.trim().equals("")) {
				userKeys.add(password);
			}
			else 
				errorMessage += " Password non inserita";
			
			if(errorMessage.equals(""))	{
				try {
					UserDao userDao = new UserDao(pool);
					userBean = userDao.doRetrieveByKey(userKeys);					
					//Autenticazione dell'utente con redirezione
					if(userBean != null && userBean.getId()!= -1) {
						session.setAttribute("user", userBean);
						response.sendRedirect(getServletContext().getContextPath()+"/home");
					}
					else {
						errorMessage = "Email o password inserita non valida"; 
						request.setAttribute("errorMessage", errorMessage);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
						rd.forward(request, response);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			else {
				//Rimando al login in caso di errore
				request.setAttribute("errorMessage", errorMessage);
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
			}
		}
		
	}
}
