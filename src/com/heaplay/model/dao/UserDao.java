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
import com.heaplay.model.beans.UserBean;

public class UserDao implements DaoModel {

	private static final String TABLE_NAME="users";
	private ConnectionPool pool = null;
	
	
	public UserDao(ConnectionPool pool) {
		this.pool = pool;
	
	}
	
	@Override
	public void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String insertQuery = "INSERT INTO " + TABLE_NAME + " (username,email,password,auth,active) VALUES (?,?,MD5(?),?,?)";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			UserBean userBean = (UserBean) bean;

			ps.setString(1, userBean.getUsername());
			ps.setString(2, userBean.getEmail());
			ps.setString(3, userBean.getPassword());
			ps.setString(4, userBean.getAuth());
			ps.setBoolean(5, userBean.isActive());
			
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
	
	/*id integer unsigned auto_increment not null,
    username varchar(255) not null unique,
    email varchar(255) not null unique,
    password varchar(255) not null,
    auth enum('user', 'admin') default 'user',
    active boolean default true,
    primary key(id) */

	@Override
	public void doUpdate(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String updateQuery = "UPDATE " + TABLE_NAME + " SET email=?,username=?,password=?,auth=?,active=?  WHERE id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			UserBean userBean = (UserBean) bean;
			
			ps.setString(1, userBean.getEmail());
			ps.setString(2, userBean.getUsername());
			ps.setString(3, userBean.getPassword());
			ps.setString(4, userBean.getAuth());
			ps.setBoolean(5, userBean.isActive());
			ps.setLong(6, Long.parseLong(userBean.getKey().get(0)));
	
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
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(deleteQuery);
		
			ps.setLong(1, Long.parseLong(keys.get(0)));
			
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
	public UserBean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		UserBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE email=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setString(1, keys.get(0));
			
			rs = ps.executeQuery();
			
			if(rs.next()) {
				bean = new UserBean();
				bean.setUsername(rs.getString("username"));
				bean.setId(rs.getLong("id"));
				bean.setEmail(rs.getString("email"));
				bean.setAuth(rs.getString("auth"));
				bean.setPassword(rs.getString("password"));
				bean.setActive(rs.getBoolean("active"));
			}
			
			if(rs != null)
				rs.close();
			
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
		UserBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while(rs.next()) {
				bean = new UserBean();
				bean.setUsername(rs.getString("username"));
				bean.setId(rs.getLong("id"));
				bean.setEmail(rs.getString("email"));
				bean.setAuth(rs.getString("auth"));
				bean.setPassword(rs.getString("password"));
				bean.setActive(rs.getBoolean("active"));
				list.add(bean);
			}
			
			list.sort(comparator);
			
			if(rs != null)
				rs.close();
			
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
