package com.heaplay.model.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PlaylistBean implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String privacy;
	private long author;
	private List<TrackBean> tracks;
	
	public PlaylistBean() {
		id = author = -1;
		name = privacy = "";
		tracks = new ArrayList<TrackBean>();
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrivacy() {
		return privacy;
	}
	
	public void setPrivacy(String privacy) {
		this.privacy = privacy;
	}
	
	public long getAuthor() {
		return author;
	}
	
	public void setAuthor(long author) {
		this.author = author;
	}
	
	public List<TrackBean> getTracks() {
		return tracks;
	}
	
	public void setTracks(List<TrackBean> tracks) {
		this.tracks = tracks;
	}
	
	@Override
	public boolean equals(Object otherOb) {
		if(otherOb == null || otherOb.getClass().getName()!=getClass().getName())
			return false;
		PlaylistBean other = (PlaylistBean) otherOb;
		return other.id == id;
	}

	@Override
	public PlaylistBean clone() {
		PlaylistBean bean = null;
		try {
			bean = (PlaylistBean) super.clone();
			bean.tracks = new ArrayList<TrackBean>();
			for (int i=0; i < tracks.size() ; i++ )
				bean.tracks.add(tracks.get(i).clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public String toString() {
		return  getClass().getSimpleName() +" [id=" + id + ", name=" + name + ", privacy=" + privacy + ", author=" + author + ", tracks="
				+ tracks + "]";
	}
	
	

}