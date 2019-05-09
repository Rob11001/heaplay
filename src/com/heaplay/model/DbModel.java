package com.heaplay.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;

public interface DbModel<T, K> {

	public void doSave(T bean) throws SQLException;

	public boolean doDelete(Collection<String> keys) throws SQLException;

	public T doRetrieveByKey(Collection<String> keys) throws SQLException;
	
	public Collection<T> doRetrieveAll(Comparator<T> comparator) throws SQLException;
}
