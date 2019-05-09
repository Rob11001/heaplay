package com.heaplay.model.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

public class TagBean implements Serializable,Cloneable, Key<TagBean> {

	private static final long serialVersionUID = 1L;

	private long id;
	private String name;
	
	public TagBean () {
		id = -1;
		name = "";
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
	
	@Override
	public boolean equals(Object otherOb) {
		if(otherOb == null || otherOb.getClass().getName()!=getClass().getName())
			return false;
		TagBean other = (TagBean) otherOb;
		return other.id == id;
	}

	@Override
	public TagBean clone() {
		TagBean bean = null;
		try {
			bean = (TagBean) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return bean;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [id=" + id + ", name=" + name + "]";
	}
	
	/**
	 * Ritorna (this.id)
	 */
	@Override
	public Collection<String> getKey() {
		ArrayList<String> key = new ArrayList<String>();
		key.add(String.valueOf(id));
		return key;
	}

	/**
	 * Ritorna this.id - otherBean.id
	 */
	@Override
	public int compareKey(TagBean otherBean) {
		return (int) (id - otherBean.getId());
	}

}
