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
import com.heaplay.model.beans.PlaylistBean;
import com.heaplay.model.beans.TrackBean;

public class PlaylistDao implements DaoModel {


	private static final String TABLE_NAME_1 = "playlists";
	private static final String TABLE_NAME_2 = "contains";
	private ConnectionPool pool = null;
	
	
	public PlaylistDao(ConnectionPool pool) {
		this.pool = pool;
	}
	
	
	@Override
	public synchronized void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String insertQuery1 = "INSERT INTO " + TABLE_NAME_1 + " (name,privacy,author) VALUES (?,?,?)";
		String insertQuery2 = "INSERT INTO " + TABLE_NAME_2 + " (track_id, playlist_id) VALUES (?,?)";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery1);
			PlaylistBean playlistBean = (PlaylistBean) bean;

			ps.setString(1, playlistBean.getName());
			ps.setString(2, playlistBean.getPrivacy());
			ps.setLong(3, playlistBean.getAuthor());
			
			int result = ps.executeUpdate();
			ps.close();			
			
			ps = con.prepareStatement(insertQuery2);
			for (TrackBean b : playlistBean.getTracks()) {
				ps.clearParameters();
				ps.setLong(1, b.getId());
				ps.setLong(2, playlistBean.getId());
				ps.executeUpdate();
			}
			
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
	public synchronized void doUpdate(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		
		String updateQuery1 = "UPDATE " + TABLE_NAME_1 + " SET name=?,privacy=?,author=?  WHERE id=?";
		String insertContains = "INSERT INTO " + TABLE_NAME_2 + " (playlist_id, track_id) VALUES (?, ?)";
		String deleteContains = "DELETE FROM " + TABLE_NAME_2 + " WHERE playlist_id = ? AND track_id = ?";
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery1);
			PlaylistBean playlistBean = (PlaylistBean) bean;
			
			ps.setString(1, playlistBean.getName());
			ps.setString(2, playlistBean.getPrivacy());
			ps.setLong(3, playlistBean.getAuthor());
			ps.setLong(4, Long.parseLong(playlistBean.getKey().get(0)));
	
			int result = ps.executeUpdate();
			
			if(ps != null)
				ps.close();
						
			TrackDao trackDao = new TrackDao(pool);
			ArrayList<TrackBean> tracks = trackDao.getTracksByPlaylist(playlistBean.getId());
			ps = con.prepareStatement(insertContains);
			
			for(TrackBean track : playlistBean.getTracks()) {
				if(!tracks.contains(track)) {
					ps.clearParameters();
					ps.setLong(1, playlistBean.getId());
					ps.setLong(2, track.getId());
					ps.executeUpdate();
				}
			}
			
			if (ps != null)
				ps.close();
			
			ps = con.prepareStatement(deleteContains);
			tracks.removeAll(playlistBean.getTracks());
			if (tracks.size() != 0) {
				for (TrackBean track : tracks) {
					ps.clearParameters();
					ps.setLong(1, playlistBean.getId());
					ps.setLong(2, track.getId());
					ps.executeUpdate();
				}
			}
			
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
	public synchronized boolean doDelete(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		String deleteQuery = "DELETE FROM " + TABLE_NAME_1 + " WHERE id=?";
		
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
	public synchronized Bean doRetrieveByKey(List<String> keys) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		PlaylistBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME_1 + " WHERE id=?";
		
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
		
			ps.setString(1, keys.get(0));
			
			rs = ps.executeQuery();
			
			TrackDao trackDao = new TrackDao(pool);
			
			
			if(rs.next()) {
				bean = new PlaylistBean();
				bean.setId(rs.getLong("id"));
				bean.setName(rs.getString("username"));
				bean.setPrivacy("privacy");
				bean.setAuthor(rs.getLong("author"));
				bean.setTracks(trackDao.getTracksByPlaylist(bean.getId()));				
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
	public synchronized List<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null; 
		List<Bean> list =  new ArrayList<Bean>(); 
		PlaylistBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME_1;
		
		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			TrackDao trackDao = new TrackDao(pool);
			
			while(rs.next()) {
				bean = new PlaylistBean();
				bean.setName(rs.getString("username"));
				bean.setAuthor(rs.getLong("author"));
				bean.setPrivacy(rs.getString("privacy"));
				bean.setTracks(trackDao.getTracksByPlaylist(bean.getId()));
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
