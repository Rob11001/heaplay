package com.project.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class UserModelDM implements UserModel {
	
	public static final String TABLE_NAME = "users";
	public ConnectionPool pool;
	
	public UserModelDM(ConnectionPool pool) {
		this.pool = pool;
	}

	@Override
	public void doSave(UserBean user) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		PreparedStatement preparedStatement2 = null;

		String insertSQL = "insert into " + TABLE_NAME
				+ " (username, email, password, auth, active) VALUES (?, ?, MD5(?), ?, ?, ?)";

		String selectSQL = "select password from " + TABLE_NAME + " where email = ?";
		
		try {
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(insertSQL);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPassword());
			preparedStatement.setString(4, user.getAuth());
			preparedStatement.setBoolean(5, user.isActive());

			preparedStatement.executeUpdate();
			connection.commit();
			
			preparedStatement2 = connection.prepareStatement(selectSQL);
			preparedStatement2.setString(1, user.getEmail());
			
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			user.setPassword(rs.getString("password"));
			
		} finally {
			try {
				if (preparedStatement != null)
					preparedStatement.close();
			} finally {
				pool.releaseConnection(connection);
			}
		}

	}

	@Override
	public boolean doDelete(int id) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public UserBean doRetrieveByKey(String email) throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		
		UserBean user = new UserBean();
		
		String selectSQL = "select * from " + TABLE_NAME + " where email = ?";
		
		try {
			connection = pool.getConnection();
			preparedStatement = connection.prepareStatement(selectSQL);
			preparedStatement.setString(1, email);

			ResultSet rs = preparedStatement.executeQuery();
			while(rs.next()) {
				user.setId(rs.getLong("id"));
				user.setUsername(rs.getString("username"));
				user.setPassword(rs.getString("email"));
				user.setEmail(rs.getString("email"));
				user.setAuth(rs.getString("auth"));
				user.setActive(rs.getBoolean("active"));
			}
			
		} finally {
			try {
				if(preparedStatement != null)
					preparedStatement.close();
			} finally {
				pool.releaseConnection(connection);
			}
		}
		
		return user;		
	}

	@Override
	public Collection<UserBean> doRetrieveAll(String order) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

}
