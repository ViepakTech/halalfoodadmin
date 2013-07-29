package com.viepak.halalfood.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.viepak.halalfood.client.service.UserManagement;
import com.viepak.halalfood.db.UserDataUtility;
import com.viepak.halalfood.db.UserDataUtilityGoogle;
import com.viepak.halalfood.shared.User;
import com.viepak.halalfood.shared.UserRole;

@SuppressWarnings("serial")
public class UserManagementImpl extends RemoteServiceServlet implements UserManagement {
	
	@Override
	public User createUser(User user) {
		return UserDataUtilityGoogle.CreateUser(user);
	}

	@Override
	public Boolean updateUser(User user) {
		return UserDataUtility.UpdateUser(user);
	}

	@Override
	public Boolean deleteUser(User user) {
		return UserDataUtility.DeleteUser(user.getId());
	}

	@Override
	public List<User> getAllUser() {
		return UserDataUtilityGoogle.GetAllUsers();
	}

	@Override
	public User login(String userName, String password) {
		
		if(userName.equals("admin") && password.equals("password")){
			return getUser1();
		}else{
			return null;
		}
	}

	@Override
	public Boolean logout(User user) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private User getUser1(){
		User user = new User();
		user.setName("Administrator");
		user.setEmail("admin@gwt.com");
		user.setIsActive(true);
		user.setPhoneNumber("(123) 123-123");
		user.setRole(UserRole.SuperAdmin);
		
		return user;
	}
}
