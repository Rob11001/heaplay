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

@WebServlet("/registerAdmin")
public class RegisterAdmin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		if(userBean != null && !userBean.getAuth().equals("admin"))														
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			request.setAttribute("jspPath", "admin/register_new_admin.jsp");
			request.setAttribute("pageTitle", "Registra admin");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");	
			rd.forward(request, response);
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
    	UserBean userBean = new UserBean();
    	String username = request.getParameter("username");				//Lettura parametri
    	String password = request.getParameter("password");
    	String email = request.getParameter("email");
    	String error = "";
    	
    	//Controllo parametri
    	if(username != null && !username.trim().equals(""))
    		request.setAttribute("username", username);
    	else
    		error += "Username non inserito";
  
    	if(password != null && !password.trim().equals(""))
    		request.setAttribute("password", password);
    	else 
    		error += " Password non inserita";
    	
    	if(email != null && !email.trim().equals(""))
    		request.setAttribute("email", email);
    	else
    		error += "Email non inserita";
    	
    	//Creazione dell'account
    	if(error.equals("")) {
    		userBean.setEmail(email);
    		userBean.setPassword(password);
    		userBean.setUsername(username);
    		userBean.setActive(true);
    		userBean.setAuth("admin");
    		UserDao userDao = new UserDao(pool);
    		
    		try {
				userDao.doSave(userBean);
				ArrayList<String> keys = new ArrayList<String>();
				keys.add(email);
				keys.add(password);											//Problemi nel commit della query
				userBean = userDao.doRetrieveByKey(keys);
					
			} catch (SQLException e) {
				error = "Email o username già presenti"; 
				e.printStackTrace();
			}
    		
    		//Creazione riuscita e redirezione
			if(userBean != null && userBean.getId() != -1) {
				request.getSession().setAttribute("created", "true");
				response.sendRedirect(getServletContext().getContextPath()+"/operation?op=register");
			}
			else {
				//Errore creazione e rinvio alla pagina di registrazione
				error = "Email o username già presenti"; 
				request.setAttribute("errorMessage", error);
				request.setAttribute("jspPath", "admin/register_new_admin.jsp");
				request.setAttribute("pageTitle", "Registra admin");
				RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");	
				rd.forward(request, response);
			}
    	}
	}

}
