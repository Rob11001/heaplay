package com.heaplay.model.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * DTO della Track table
 * 
 */
public class TrackBean implements Serializable,Cloneable {

	private static final long serialVersionUID = 1L;
	
	private long id;
	private String name;
	private String type;
	private long plays;
	private byte[] track;
	private String trackExt;
	private byte[] image;
	private String imageExt;
	private Date uploadDate;
	private long likes;
	private boolean indexable;
	private long author;
	
	public TrackBean() {
		this.id = this.plays = this.likes = this.author = -1;
		this.name = this.type = this.trackExt = this.imageExt = "";
		this.track = null;
		this.image = null;
		this.uploadDate = null;
		this.indexable = false;
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
	
	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
	public long getPlays() {
		return plays;
	}
	
	public void setPlays(long plays) {
		this.plays = plays;
	}
	
	public byte[] getTrack() {
		return track;
	}
	
	public void setTrack(byte[] track) {
		this.track = track;
	}
	
	public String getTrackExt() {
		return trackExt;
	}
	
	public void setTrackExt(String trackExt) {
		this.trackExt = trackExt;
	}
	
	public byte[] getImage() {
		return image;
	}
	
	public void setImage(byte[] image) {
		this.image = image;
	}
	
	public String getImageExt() {
		return imageExt;
	}
	
	public void setImageExt(String imageExt) {
		this.imageExt = imageExt;
	}
	
	public Date getUploadDate() {
		return (Date) uploadDate.clone();
	}
	
	public void setUploadDate(Date uploadDate) {
		this.uploadDate = (Date) uploadDate.clone();
	}
	
	public long getLikes() {
		return likes;
	}
	
	public void setLikes(long likes) {
		this.likes = likes;
	}
	
	public boolean isIndexable() {
		return indexable;
	}
	
	public void setIndexable(boolean indexable) {
		this.indexable = indexable;
	}
	
	public long getAuthor() {
		return author;
	}
	
	public void setAuthor(long author) {
		this.author = author;
	}
	
	@Override
	public boolean equals(Object otherOb) {
		if(otherOb == null || otherOb.getClass().getName()!=getClass().getName())
			return false;
		TrackBean other = (TrackBean) otherOb;
		return other.id == id;
	}

	@Override
	public TrackBean clone() {
		TrackBean bean = null;
		try {
			bean = (TrackBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", name=" + name + ", type=" + type + ", plays=" + plays + ", trackExt="
				+ trackExt + ", imageExt=" + imageExt + ", uploadDate=" + uploadDate + ", likes=" + likes
				+ ", indexable=" + indexable + ", author=" + author + "]";
	}
	
	
	
}
