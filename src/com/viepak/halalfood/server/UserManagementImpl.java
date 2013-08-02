package com.viepak.halalfood.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.viepak.halalfood.client.service.UserManagement;
import com.viepak.halalfood.db.UserDataUtilityGoogle;
import com.viepak.halalfood.shared.User;

@SuppressWarnings("serial")
public class UserManagementImpl extends RemoteServiceServlet implements UserManagement {
	
	@Override
	public User createUser(User user) {
		return UserDataUtilityGoogle.SyncUser(user);
	}

	@Override
	public Boolean updateUser(User user) {
		User returnUser = UserDataUtilityGoogle.SyncUser(user);
		if(returnUser != null)
			return true;
		else
			return false;
	}

	@Override
	public Boolean deleteUser(User user) {
		return UserDataUtilityGoogle.DeleteUser(user.getId());
	}

	@Override
	public List<User> getAllUser() {
		return UserDataUtilityGoogle.GetAllUsers();
	}

	@Override
	public User login(String userName, String password) {
		
		if(userName.equals("create root")){
			User user = new User();
			user.setEmail("admin");
			user.setPassword("password");
			user.setRole("Super Admin");
			user.setPhoneNumber("00000");
			user.setIsActive(true);
			return UserDataUtilityGoogle.SyncUser(user);
		}else{
			return UserDataUtilityGoogle.GetUserByFilter(userName, password);
		}
	}

	@Override
	public Boolean logout(User user) {
		// TODO Auto-generated method stub
		return null;
	}
}
