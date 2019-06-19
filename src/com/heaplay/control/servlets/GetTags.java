package com.heaplay.control.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.TagBean;
import com.heaplay.model.dao.TagDao;



@WebServlet("/getTags")
public class GetTags extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	//Non funziona capire perchè
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ConnectionPool pool = (ConnectionPool) getServletContext().getAttribute("pool");
		TagDao tagDao = new TagDao(pool);
		try {
			List<Bean> list = tagDao.doRetrieveAll(null);
			JSONArray tagNames = new JSONArray();
			for(int i = 0; i < list.size() ; i++) {
				String name = ((TagBean)list.get(i)).getName();
				tagNames.put(name);
			}
			JSONObject object = new JSONObject();
			object.put("tags",tagNames);
			String jsonString = object.toString();
			System.out.println(jsonString);	
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	

}
