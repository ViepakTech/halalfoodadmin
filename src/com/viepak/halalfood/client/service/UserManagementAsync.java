package com.viepak.halalfood.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.viepak.halalfood.shared.User;

public interface UserManagementAsync {

	void createUser(User user, AsyncCallback<User> callback);

	void updateUser(User user, AsyncCallback<Boolean> callback);

	void deleteUser(String id, AsyncCallback<Boolean> callback);

	void getAllUser(AsyncCallback<List<User>> callback);

	void login(String userName, String password, AsyncCallback<User> callback);

	void logout(User user, AsyncCallback<Boolean> callback);
	
}
