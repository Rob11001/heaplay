package com.heaplay.model.beans;

public class PurchasableTrackBean extends TrackBean {

	private static final long serialVersionUID = 1L;

	private long sold;
	private double price;
	
	public PurchasableTrackBean () {
		super();
		sold = -1;
		price = 0;
	}
	
	public long getSold() {
		return sold;
	}
	
	public void setSold(long sold) {
		this.sold = sold;
	}
	
	public double getPrice() {
		return price;
	}
	
	public void setPrice(double price) {
		this.price = price;
	}
	
	@Override
	public boolean equals(Object otherOb) {
		return super.equals(otherOb);
	}

	@Override
	public PurchasableTrackBean clone() {
		PurchasableTrackBean bean = (PurchasableTrackBean) super.clone();
		return bean;
	}

	@Override
	public String toString() {
		return super.toString()+"[sold=" + sold + ", price=" + price + "]";
	}
}
