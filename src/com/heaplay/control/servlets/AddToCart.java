package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
import com.heaplay.model.dao.PurchasableTrackDao;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/addToCart")
public class AddToCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String track_id = request.getParameter("track_id");
    	HttpSession session = request.getSession();
    	UserBean user = (UserBean) session.getAttribute("user");
    	Cart<TrackBean> cart = (Cart<TrackBean>) session.getAttribute("cart");
    	String remove = request.getParameter("remove");
    	
    	if(track_id == null || user == null) 
    		response.sendRedirect(getServletContext().getContextPath()+"/home");
    	else {
    		try {
				ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
				TrackDao trackDao = new TrackDao(pool);
				if(cart == null) {
					cart = new Cart<TrackBean>();
					cart.setItems(trackDao.getCart(user.getId()));
					session.setAttribute("cart", cart);
				}
				if(remove != null) {
					int size = cart.getItems().size();
					ArrayList<TrackBean> list = (ArrayList<TrackBean>) cart.getItems();
					list = (ArrayList<TrackBean>) list.stream().filter((p) -> p.getId() != Long.parseLong(track_id)).collect(Collectors.toList());
					if(size != list.size()) {
						cart.setItems(list);
						trackDao.saveCart(cart.getItems(),user.getId());
					}
				} else {
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(track_id);
					TrackBean trackToAdd = (TrackBean) trackDao.doRetrieveByKey(keys);
					if(trackToAdd.getType().equals("pagamento")) {
						PurchasableTrackDao pTrackDao = new PurchasableTrackDao(pool);
						trackToAdd = (TrackBean) pTrackDao.doRetrieveByKey(keys);
					}

					ArrayList<TrackBean> list = (ArrayList<TrackBean>) cart.getItems().stream().filter((p) -> p.getId() != Long.parseLong(track_id)).collect(Collectors.toList());
					if(list.size() ==  cart.getItems().size()) {
						cart.addItem(trackToAdd);
						trackDao.saveCart(cart.getItems(), user.getId());
					}
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
