package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.CommentBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.CommentDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/getComments")
public class GetComments extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String track_id = request.getParameter("track_id");
		String begin = request.getParameter("begin");
		
		if(track_id == null || begin == null)
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			try {
				int start = (Integer.parseInt(begin) < 0) ? 0 : Integer.parseInt(begin);
				ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
				CommentDao commentDao = new CommentDao(pool);
				ArrayList<Bean> list = (ArrayList<Bean>) commentDao.getCommentsByTrack(Long.parseLong(track_id),start,10);
				Gson gson = new Gson();
				String object = gson.toJson(list);
				response.getWriter().write(object);
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
				
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
