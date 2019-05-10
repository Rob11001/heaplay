package com.heaplay.model;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;

import com.heaplay.model.beans.Bean;

public interface DbModel {

	public void doSave(Bean bean) throws SQLException;

	public boolean doDelete(Collection<String> keys) throws SQLException;

	public Bean doRetrieveByKey(Collection<String> keys) throws SQLException;
	
	public Collection<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException;
}
