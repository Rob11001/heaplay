package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TagDao;

@WebServlet("/removeTag")
public class RemoveTag extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserBean user = (UserBean) request.getSession().getAttribute("user");
		String tag_id = request.getParameter("tag_id");
		
		if(user == null || !user.getAuth().equals("admin") || tag_id == null)
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			TagDao tagDao = new TagDao((ConnectionPool) getServletContext().getAttribute("pool"));
			ArrayList<String> keys = new ArrayList<String>();
			keys.add(tag_id);
			try {
				tagDao.doDelete(keys);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
