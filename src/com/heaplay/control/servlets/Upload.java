package com.heaplay.control.servlets;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.PurchasableTrackBean;
import com.heaplay.model.beans.TagBean;
import com.heaplay.model.beans.TrackBean;
import com.heaplay.model.beans.UserBean;
import com.heaplay.model.dao.PurchasableTrackDao;
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
		// Implementare upload e aggiungere i controlli necessari
		String trackName = request.getParameter("songName");
		String radioBox = request.getParameter("purchasable");
		String id = request.getParameter("authorId");
		//Aggiustare i tags
		String tags = request.getParameter("tags");
		ArrayList<TagBean> listTags = new ArrayList<TagBean>();
		TagBean tagBean = new TagBean();
		tagBean.setName(tags);
		listTags.add(tagBean);
		
		Part audio = request.getPart("audio");
		String audioFileName = audio.getSubmittedFileName();
		System.out.println(audioFileName);
		String audioExt=audioFileName.substring(audioFileName.lastIndexOf('.'),audioFileName.length()).toLowerCase();
		InputStream audioStream =audio.getInputStream();
		byte[] audioBytes = audioStream.readAllBytes();
		double durationInSeconds = 0;
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new BufferedInputStream(audioStream));
			long frameLen = audioInputStream.getFrameLength();
			AudioFormat format = audioInputStream.getFormat();
			durationInSeconds = (frameLen+0.0) / format.getFrameRate();
		} catch (UnsupportedAudioFileException e1) {
			e1.printStackTrace();
		}
		audioStream.close();

		
		Part image = request.getPart("image");
		String imageFileName = image.getSubmittedFileName();
		System.out.println(imageFileName);
		String imageExt=imageFileName.substring(imageFileName.lastIndexOf('.'),imageFileName.length()).toLowerCase();
		InputStream imageStream =image.getInputStream();
		byte[] imageBytes = imageStream.readAllBytes();
		imageStream.close();
		
		TrackBean trackBean = new TrackBean();
		PurchasableTrackBean purchasableTrack = null;
		trackBean.setAuthor(Long.parseLong(id));
		trackBean.setIndexable(true);
		trackBean.setName(trackName);
		trackBean.setTrack(audioBytes);
		trackBean.setTrackExt(audioExt);
		trackBean.setImage(imageBytes);
		trackBean.setImageExt(imageExt);
		trackBean.setUploadDate(new Timestamp(System.currentTimeMillis()));
		trackBean.setTags(listTags);
		trackBean.setDuration((int)Math.floor(durationInSeconds));
		if(radioBox.equalsIgnoreCase("Gratis"))
			trackBean.setType("free");
		else {
			trackBean.setType("pagamento");
			double price = Double.parseDouble(request.getParameter("price"));		//Dovremmo controllare che il prezzo passato è valido
			purchasableTrack = new PurchasableTrackBean(trackBean);
			purchasableTrack.setPrice(price);
		}
		
		try {
			TrackDao trackDao = new TrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
			trackDao.doSave(trackBean);
			if(purchasableTrack != null) {
				PurchasableTrackDao purchasableTrackdao = new PurchasableTrackDao((ConnectionPool) getServletContext().getAttribute("pool"));
				purchasableTrackdao.doSave(purchasableTrack);
			}
			response.sendRedirect(getServletContext().getContextPath()+"/home");	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
