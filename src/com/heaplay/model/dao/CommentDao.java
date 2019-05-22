package com.heaplay.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.heaplay.model.ConnectionPool;
import com.heaplay.model.beans.Bean;
import com.heaplay.model.beans.CommentBean;

public class CommentDao implements DaoModel {
	
	private static final String TABLE_NAME="comments";
	private ConnectionPool pool = null;

	public CommentDao(ConnectionPool pool) {
		this.pool = pool;
	
	}
	
	@Override
	public void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String insertQuery = "INSERT INTO " + TABLE_NAME + " (id,track_id,user_id,body) VALUES (?,?,?,?)";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			CommentBean commentBean = (CommentBean) bean;

			ps.setLong(1, commentBean.getId());
			ps.setLong(2, commentBean.getTrackId());
			ps.setLong(3, commentBean.getUserId());
			ps.setString(4, commentBean.getBody());
			
			int result = ps.executeUpdate();
			
			if(result != 0)
				con.commit();
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
	}

	@Override
	public void doUpdate(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET body=? WHERE id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			CommentBean commentBean = (CommentBean) bean;
			
			ps.setString(1, commentBean.getBody());		
			ps.setLong(2, Long.parseLong(commentBean.getKey().get(0)));
			ps.setLong(3, Long.parseLong(commentBean.getKey().get(1)));

			int result = ps.executeUpdate();
			
			if(result != 0)
				con.commit();
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
	}

	@Override
	public boolean doDelete(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(deleteQuery);
		
			ps.setLong(1, Long.parseLong(keys.get(0)));
			ps.setLong(2, Long.parseLong(keys.get(1)));

			result = ps.executeUpdate();
			con.commit();
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return (result != 0);
	}

	@Override
	public Bean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		CommentBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setString(1, keys.get(0));
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new CommentBean();
				bean.setBody(rs.getString("body"));
				bean.setId(rs.getLong("id"));
				bean.setTrackId(rs.getLong("track_id"));
				bean.setUserId(rs.getLong("user_id"));
			}
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return bean;
	}

	@Override
	public List<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		List<Bean> list =  new ArrayList<Bean>(); 
		CommentBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new CommentBean();
				bean.setBody(rs.getString("body"));
				bean.setId(rs.getLong("id"));
				bean.setTrackId(rs.getLong("track_id"));
				bean.setUserId(rs.getLong("user_id"));
				list.add(bean);
			}
			
			list.sort(comparator);
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		
		return list;
	}

}
