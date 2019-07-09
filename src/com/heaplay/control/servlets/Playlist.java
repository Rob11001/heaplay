package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PlaylistDao;
import com.heaplay.model.dao.UserDao;

@WebServlet("/filter/playlist")
public class Playlist extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = (String) request.getAttribute("userName");
		String playlistName = (String) request.getAttribute("playlistName");
		String begin = request.getParameter("begin");
		String id = request.getParameter("id"); 
		UserBean currentUser = (UserBean) request.getSession().getAttribute("user");
		StringBuffer requestURL = (StringBuffer) request.getAttribute("requestURL");
		
		if(user == null || playlistName == null)
			response.sendRedirect(response.encodeRedirectURL(getServletContext().getContextPath()+"/home"));
		else {
			try {
				int start = (begin == null) ? 0 : Integer.parseInt(begin);
				ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
				UserDao userDao = new UserDao(pool);
				PlaylistDao playlistDao = new PlaylistDao(pool);
				
				UserBean userBean = userDao.doRetrieveByName(user);
				if(userBean == null) {
					/*Pagina di errore*/
					request.setAttribute("error_title", "Pagina non trovata - 404");
					request.setAttribute("error", "La pagina \""+ requestURL + "\" non è stata trovata o non esiste");
					response.sendError(response.SC_NOT_FOUND);
				} else {
					ArrayList<String> keys = new ArrayList<String>();
					keys.add(id);
					PlaylistBean playlistBean = (PlaylistBean) playlistDao.doRetrieveByKey(keys);
					if(playlistBean == null || !playlistBean.getName().replaceAll("\\s","").equals(playlistName) || !playlistBean.getAuthorName().equals(user) || (playlistBean.getPrivacy().equals("private") && (currentUser == null || !playlistBean.getAuthorName().equals(currentUser.getUsername())))) {
						/*Pagina di errore*/
						request.setAttribute("error_title", "Pagina non trovata - 404");
						request.setAttribute("error", "La pagina \""+ requestURL + "\" non è stata trovata o non esiste");
						response.sendError(response.SC_NOT_FOUND);
					} else {
						ArrayList<TrackBean> list = (ArrayList<TrackBean>) playlistBean.getTracks();
						int size = list.size();
						if(playlistBean.getPrivacy().equals("public"))
							list = (ArrayList<TrackBean>) list.stream().filter((p) ->((TrackBean)p).isIndexable()).collect(Collectors.toList());
						if(size > list.size()) {
							//Elimino quando serve le track non più indexable dalla playlist
							size = list.size();
							playlistBean.setTracks(list);
							playlistDao.doUpdate(playlistBean);
						}
						ArrayList<TrackBean> sublist = new ArrayList<TrackBean>();
						sublist.addAll(list.subList(start, list.size() < start+10 ? list.size() : start+10));
						playlistBean.setTracks(sublist);
						request.setAttribute("userPage", userBean);
						request.setAttribute("playlist", playlistBean);
						request.setAttribute("number", size);
						request.setAttribute("begin", start);
						request.setAttribute("jspPath", response.encodeURL("/playlist.jsp"));
						request.setAttribute("pageTitle", playlistBean.getName());
						RequestDispatcher rd = getServletContext().getRequestDispatcher(response.encodeURL("/_blank.jsp"));
						rd.forward(request, response);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
				response.sendError(response.SC_INTERNAL_SERVER_ERROR);
			}
			
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
