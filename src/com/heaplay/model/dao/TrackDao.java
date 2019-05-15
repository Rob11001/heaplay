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
import com.heaplay.model.beans.TagBean;
import com.heaplay.model.beans.TrackBean;

public class TrackDao implements DaoModel {

	private static final String TABLE_NAME = "tracks";
	private ConnectionPool pool = null;

	public TrackDao(ConnectionPool pool) {
		this.pool = pool;
	}

	@Override
	public void doSave(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;

		String insertQuery = "INSERT INTO " + TABLE_NAME
				+ " (name,type,plays,track,track_extension,image,image_extension,indexable,author,upload,likes) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
		String insertTags = "INSERT INTO  TAGGED(track_id,tag_id) VALUES (?,?) ";

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(insertQuery);
			TrackBean trackBean = (TrackBean) bean;

			ps.setString(1, trackBean.getName());
			ps.setString(2, trackBean.getType());
			ps.setLong(3, trackBean.getPlays());
			ps.setBytes(4, trackBean.getTrack());
			ps.setString(5, trackBean.getTrackExt());
			ps.setBytes(6, trackBean.getImage());
			ps.setString(7, trackBean.getImageExt());
			ps.setBoolean(8, trackBean.isIndexable());
			ps.setLong(9, trackBean.getAuthor());
			ps.setTimestamp(10,trackBean.getUploadDate());
			ps.setLong(11, trackBean.getLikes());

			int result = ps.executeUpdate();

			if (ps != null)
				ps.close();

			ps = con.prepareStatement(insertTags);

			for (TagBean tag : trackBean.getTags()) {
				ps.clearParameters();
				ps.setLong(1, trackBean.getId());
				ps.setLong(2, tag.getId());
				ps.executeUpdate();
			}

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
	public void doUpdate(Bean bean) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;

		String updateQuery = "UPDATE " + TABLE_NAME
				+ " SET  name=?,type=?,plays=?,track=?,track_extension=?,image=?,image_extension=?,indexable=?,author=?,upload=?,likes=? WHERE id=?";
		String insertTag = "INSERT INTO  TAGGED(track_id,tag_id) VALUES (?,?) ";
		String deleteTag = "DELETE FROM TAGGED WHERE track_id=? AND tag_id=?";

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(updateQuery);
			TrackBean trackBean = (TrackBean) bean;

			ps.setString(1, trackBean.getName());
			ps.setString(2, trackBean.getType());
			ps.setLong(3, trackBean.getPlays());
			ps.setBytes(4, trackBean.getTrack());
			ps.setString(5, trackBean.getTrackExt());
			ps.setBytes(6, trackBean.getImage());
			ps.setString(7, trackBean.getImageExt());
			ps.setBoolean(8, trackBean.isIndexable());
			ps.setLong(9, trackBean.getAuthor());
			ps.setTimestamp(10, trackBean.getUploadDate());
			ps.setLong(11, trackBean.getLikes());
			ps.setLong(12, trackBean.getId());

			int result = ps.executeUpdate();

			if (ps != null)
				ps.close();
			TagDao tagDao = new TagDao(pool);
			ArrayList<TagBean> tags = tagDao.getTagsByTrack(trackBean.getId());
			ps = con.prepareStatement(insertTag);

			for (TagBean tag : trackBean.getTags()) {
				if (!tags.contains(tag)) {
					ps.clearParameters();
					ps.setLong(1, trackBean.getId());
					ps.setLong(2, tag.getId());
					ps.executeUpdate();
				}
			}
			if (ps != null)
				ps.close();
			ps = con.prepareStatement(deleteTag);
			tags.removeAll(trackBean.getTags());
			if (tags.size() != 0) {
				for (TagBean tag : tags) {
					ps.clearParameters();
					ps.setLong(1, trackBean.getId());
					ps.setLong(2, tag.getId());
					ps.executeUpdate();
				}
			}

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
				if (ps != null)
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
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " WHERE id=?";

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			ps.setLong(1, Long.parseLong(keys.get(0)));

			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			if (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
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
	public List<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME;
		ArrayList<Bean> list = new ArrayList<Bean>();

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);

			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				list.add(bean);
			}

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

	public ArrayList<TrackBean> getTracksByPlaylist(Long id) throws SQLException {
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		TrackBean bean = null;
		String selectQuery = "SELECT * FROM " + TABLE_NAME + " , CONTAINS WHERE playlist_id=? AND track_id=id";
		ArrayList<TrackBean> list = new ArrayList<TrackBean>();

		try {
			con = pool.getConnection();
			ps = con.prepareStatement(selectQuery);
			ps.setLong(1, id);
			rs = ps.executeQuery();
			TagDao tag = new TagDao(pool);

			while (rs.next()) {
				bean = new TrackBean();
				bean.setId(rs.getLong("id"));
				bean.setAuthor(rs.getLong("author"));
				bean.setImage(rs.getBytes("image"));
				bean.setImageExt(rs.getString("image_extension"));
				bean.setIndexable(rs.getBoolean("indexable"));
				bean.setLikes(rs.getLong("likes"));
				bean.setName(rs.getString("name"));
				bean.setPlays(rs.getLong("plays"));
				bean.setTrack(rs.getBytes("track"));
				bean.setTrackExt(rs.getString("track_extension"));
				bean.setType(rs.getString("type"));
				bean.setUploadDate(rs.getTimestamp("upload_date"));
				bean.setTags(tag.getTagsByTrack(rs.getLong("id")));
				list.add(bean);
			}

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
