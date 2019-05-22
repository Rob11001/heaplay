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
import com.heaplay.model.beans.OwnedTrackBean;
import com.heaplay.model.beans.PurchasableTrackBean;

public class OwnedTrackDao implements DaoModel {

	private static final String TABLE_NAME = "owns";
	private ConnectionPool pool = null;
	
	public OwnedTrackDao(ConnectionPool pool) {
		this.pool = pool;
	}
	
	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		OwnedTrackBean owBean = (OwnedTrackBean) bean;
		
		String insertQuery = "INSERT INTO " + TABLE_NAME + " (user_id, purchase_date) VALUES (?, ?)";
		
		PurchasableTrackDao pDao = new PurchasableTrackDao(pool);
		pDao.doSave(owBean);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			
			ps.setLong(1, owBean.getUserId());
			ps.setTimestamp(2, owBean.getPurchaseDate());
			
			if(ps.executeUpdate() != 0)
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
	public synchronized void doUpdate(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String updateQuery = "UPDATE "+ TABLE_NAME + "SET purchase_date=? WHERE user_id=? AND track_id=?";
		PurchasableTrackDao pDao = new PurchasableTrackDao(pool);
		pDao.doUpdate(bean);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			
			OwnedTrackBean ownedBean = (OwnedTrackBean) bean;
			
			ps.setTimestamp(1, ownedBean.getPurchaseDate());
			ps.setLong(2, ownedBean.getUserId());
			ps.setLong(2, ownedBean.getId());
			
			int result = ps.executeUpdate();

			if (result != 0)
				con.commit();
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
	}

	@Override
	public synchronized boolean doDelete(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		String deleteQuery = "DELETE FROM " + TABLE_NAME + " WHERE user_id=? AND track_id=?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(deleteQuery);
			
			ps.setLong(1, Long.parseLong(keys.get(1)));
			ps.setLong(2, Long.parseLong(keys.get(0)));
			
			result = ps.executeUpdate();
			
		} finally {
			try {
				if(ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return result != 0;
	}

	@Override
	public synchronized Bean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		OwnedTrackBean bean = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE track_id=? AND user_id=?";
		PurchasableTrackDao pDao = new PurchasableTrackDao(pool);
		PurchasableTrackBean purchasableBean = (PurchasableTrackBean) pDao.doRetrieveByKey(keys.subList(0, 0));
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			ps.setLong(1, purchasableBean.getId());
			ps.setLong(2, Long.parseLong(keys.get(1)));

			rs = ps.executeQuery();
			
			if (rs.next()) {
				bean = new OwnedTrackBean(purchasableBean);
				bean.setUserId(rs.getLong("user_id"));
				bean.setPurchaseDate(rs.getTimestamp("purchase_date"));
			}

		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return bean;
	}

	@Override
	public synchronized List<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		ArrayList<Bean> list = new ArrayList<Bean>();
		ArrayList<Bean> listPurchasableTrackBean = new ArrayList<Bean>();
		OwnedTrackBean bean = null;
		
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		PurchasableTrackDao pDao = new PurchasableTrackDao(pool);
		listPurchasableTrackBean = (ArrayList<Bean>) pDao.doRetrieveAll(null);
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			
			while (rs.next()) {
				for(Bean b : listPurchasableTrackBean) {
					PurchasableTrackBean trackBean = (PurchasableTrackBean) b;
					if(Long.parseLong(b.getKey().get(0)) == rs.getLong("track_id")) { 
						bean = new OwnedTrackBean(trackBean);
						bean.setUserId(rs.getLong("user_id"));
						bean.setPurchaseDate(rs.getTimestamp("purchase_date"));
						list.add(bean);
						break;
					}	
				}
			}
			if(comparator != null)
				list.sort(comparator);
		} finally {
			try {
				if (ps != null)
					ps.close();
			} finally {
				pool.releaseConnection(con);
			}
		}
		return list;
	}

}
