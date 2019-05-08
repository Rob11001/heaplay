package com.heaplay.model;

import java.sql.SQLException;
import java.util.Collection;

import com.heaplay.model.beans.UserBean;

public interface UserModel {
	public void doSave(UserBean product) throws SQLException;

	public boolean doDelete(int id) throws SQLException;

	public UserBean doRetrieveByKey(String email) throws SQLException;
	
	public Collection<UserBean> doRetrieveAll(String order) throws SQLException;
}
