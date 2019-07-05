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

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PlaylistDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/playlist")
public class Playlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getAttribute("userName");
		String playlistName = (String) request.getAttribute("playlistName");
		String begin = request.getParameter("begin");
		String id = request.getParameter("id"); 
		
		if(user == null || playlistName == null)
			response.sendRedirect(getServletContext().getContextPath()+"/home");
		else {
			try {
				int start = (begin == null) ? 0 : Integer.parseInt(begin);
				ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
				UserDao userDao = new UserDao(pool);
				PlaylistDao playlistDao = new PlaylistDao(pool);
				
				UserBean userBean = userDao.doRetrieveByName(user);
				if(userBean == null)
					/*Pagina di errore*/;
				else {
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(id);
					PlaylistBean playlistBean = (PlaylistBean) playlistDao.doRetrieveByKey(keys);
					if(playlistBean == null || !playlistBean.getName().replaceAll("\\s","").equals(playlistName) || !playlistBean.getAuthorName().equals(user))
						/*Pagina di errore*/;
					else {
						ArrayList<TrackBean> list = (ArrayList<TrackBean>) playlistBean.getTracks();
						int size = list.size();
						ArrayList<TrackBean> sublist = new ArrayList<TrackBean>();
						sublist.addAll(list.subList(start, list.size() < start+10 ? list.size() : start+10));
						playlistBean.setTracks(sublist);
						request.setAttribute("userPage", userBean);
						request.setAttribute("playlist", playlistBean);
						request.setAttribute("number", size);
						request.setAttribute("begin", start);
						request.setAttribute("jspPath", "/playlist.jsp");
						request.setAttribute("pageTitle", playlistName);
						RequestDispatcher rd = getServletContext().getRequestDispatcher("/_blank.jsp");
						rd.forward(request, response);
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
