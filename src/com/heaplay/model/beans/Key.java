package com.heaplay.model.beans;

import java.util.Collection;

public interface Key<T> {
	
	public Collection<String> getKey();
	
	public int compareKey(T object);
}
