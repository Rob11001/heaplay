package com.heaplay.control.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.TrackDao;

@WebServlet("/upload")
@MultipartConfig(
		fileSizeThreshold = 2 * 1024 * 1024,
		maxFileSize = 30 * 1024 * 1024,
		maxRequestSize = 60 * 1024 * 1024 )

public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	HttpSession session = request.getSession();
		UserBean userBean = (UserBean) session.getAttribute("user");
		
		if(userBean != null) {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/upload.jsp");
			rd.forward(request, response);
		}
		else {
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/login.jsp");
			rd.forward(request, response);
		}
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//Bisogna aggiustare il problema di max_allowed_packet di mysql...
		// Implementare upload e aggiungere i controlli necessari
		String trackName = request.getParameter("songName");
		String radioBox = request.getParameter("purchasable");
		String id = request.getParameter("authorId");
		
		Part audio = request.getPart("audio");
		String audioFileName = audio.getSubmittedFileName();
		System.out.println(audioFileName);
		String audioExt=audioFileName.substring(audioFileName.lastIndexOf('.'),audioFileName.length()).toLowerCase();
		InputStream audioStream =audio.getInputStream();
		byte[] audioBytes = audioStream.readAllBytes();
		audioStream.close();
		
		Part image = request.getPart("image");
		String imageFileName = image.getSubmittedFileName();
		System.out.println(imageFileName);
		String imageExt=audioFileName.substring(imageFileName.lastIndexOf('.'),imageFileName.length()).toLowerCase();
		InputStream imageStream =image.getInputStream();
		byte[] imageBytes = imageStream.readAllBytes();
		imageStream.close();
		
		TrackBean trackBean = new TrackBean();
		trackBean.setAuthor(Long.parseLong(id));
		trackBean.setIndexable(true);
		//trackBean.setTags(tags);
		trackBean.setName(trackName);
		trackBean.setTrack(audioBytes);
		trackBean.setTrackExt(audioExt);
		trackBean.setType(radioBox);
		trackBean.setImage(imageBytes);
		trackBean.setImageExt(imageExt);
		trackBean.setUploadDate(new Timestamp(System.currentTimeMillis()));
		
		try {
			TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
			trackDao.doSave(trackBean);
			response.sendRedirect(getServletContext().getContextPath()+"/");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
