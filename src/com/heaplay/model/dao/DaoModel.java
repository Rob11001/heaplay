package com.heaplay.model.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Comparator;

import com.heaplay.model.beans.Bean;

public interface DaoModel {

	public void doSave(Bean bean) throws SQLException;

	public void doUpdate(Bean bean) throws SQLException;
	
	public boolean doDelete(Collection<String> keys) throws SQLException;

	public Bean doRetrieveByKey(Collection<String> keys) throws SQLException;
	
	public Collection<Bean> doRetrieveAll(Comparator<Bean> comparator) throws SQLException;
}
