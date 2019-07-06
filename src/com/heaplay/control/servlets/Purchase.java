package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Cart;
import com.heaplay.model.beans.OwnedTrackBean;
import com.heaplay.model.beans.PurchasableTrackBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.OwnedTrackDao;

@WebServlet("/purchase")
public class Purchase extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	UserBean user = (UserBean) session.getAttribute("user");
    	Cart<TrackBean> cart = (Cart<TrackBean>) session.getAttribute("cart");
    	
    	if(user == null || cart == null || cart.getItems().size() == 0)
    		response.sendRedirect(getServletContext().getContextPath()+"/home");
    	else {
    		ArrayList<TrackBean> list = (ArrayList<TrackBean>) cart.getItems();
    		OwnedTrackDao ownedTrackDao = new OwnedTrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
    		for(int i=0; i < list.size(); i++) {
    			PurchasableTrackBean purchasableTrackBean = null;
    			if(list.get(i).getType().equals("free")) 
    				purchasableTrackBean = new PurchasableTrackBean(list.get(i));
    			else 
    				purchasableTrackBean = (PurchasableTrackBean) list.get(i);
    			OwnedTrackBean track = new OwnedTrackBean(purchasableTrackBean);
    			track.setUserId(user.getId());
    			track.setPurchaseDate(new Timestamp(System.currentTimeMillis()));	
    			try {
					ownedTrackDao.doSave(track);
				} catch (SQLException e) {
					e.printStackTrace();
				}
    		}
    		cart.getItems().clear();
    	}
    		
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
