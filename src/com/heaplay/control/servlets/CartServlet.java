package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Cart;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
    	UserBean user = (UserBean) session.getAttribute("user");
    	Cart<TrackBean> cart = (Cart<TrackBean>) session.getAttribute("cart"); 
    	
    	if(user == null) 
    		response.sendRedirect(getServletContext().getContextPath()+"/login");
    	else {
    		if(cart == null) {
    			TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
    			cart = new Cart<TrackBean>();
    			try {
					cart.setItems(trackDao.getCart(user.getId()));
					session.setAttribute("cart", cart);
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    		request.setAttribute("jspPath", "/cart.jsp");
			request.setAttribute("pageTitle", "Carrello");
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
			rd.forward(request, response);
    	}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
