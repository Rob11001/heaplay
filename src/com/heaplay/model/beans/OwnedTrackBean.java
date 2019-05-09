package com.heaplay.model.beans;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class OwnedTrackBean extends PurchasableTrackBean {
	
	private static final long serialVersionUID = 1L;
	
	private long userId;
	private Date purchaseDate;
	
	public OwnedTrackBean () {
		super();
		userId = -1;
		purchaseDate = null;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
	public Date getPurchaseDate() {
		return purchaseDate;
	}
	
	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}
	
	@Override
	public boolean equals(Object otherOb) {
		return super.equals(otherOb) && ((OwnedTrackBean)otherOb).userId == userId;
	}
	
	@Override
	public OwnedTrackBean clone() {
		return (OwnedTrackBean) super.clone();
	}

	@Override
	public String toString() {
		return super.toString() + " [userId=" + userId + ", purchaseDate=" + purchaseDate + "]";
	}
	
	/**
	 * Ritorna (id, userId)
	 */
	public Collection<String> getKey() {
		ArrayList<String> keys = (ArrayList<String>) super.getKey();
		keys.add(String.valueOf(userId));
		return keys;
	}	
}
