package com.viepak.halalfood.server;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.viepak.halalfood.client.service.UserManagement;
import com.viepak.halalfood.shared.User;
import com.viepak.halalfood.shared.UserRole;

@SuppressWarnings("serial")
public class UserManagementImpl extends RemoteServiceServlet implements UserManagement {

	private static List<User> users = new ArrayList<User>();
	
	@Override
	public User createUser(User user) {
		users.add(user);
		return user;
	}

	@Override
	public Boolean updateUser(User user) {
		
		for(int count = 0; count <= users.size(); count++){
			User prevUser = users.get(count);
			if(prevUser.getId() == user.getId()){
				users.set(count, user);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public Boolean deleteUser(String id) {
		for(int count = 0; count <= users.size(); count++){
			User prevUser = users.get(count);
			if(prevUser.getId() + "" == id){
				users.remove(count);
				return true;
			}
		}
		
		return false;
	}

	@Override
	public List<User> getAllUser() {
		// TODO Auto-generated method stub
		return getUsers();
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
		user.setId(100);
		user.setName("Administrator");
		user.setEmail("admin@gwt.com");
		user.setIsActive(true);
		user.setPhoneNumber("(123) 123-123");
		user.setRole(UserRole.SuperAdmin);
		
		return user;
	}
	
	private User getUser2(){
		User user = new User();
		user.setId(200);
		user.setName("Manager");
		user.setEmail("manager@gwt.com");
		user.setIsActive(true);
		user.setPhoneNumber("(123) 123-123");
		user.setRole(UserRole.Admin);
		
		return user;
	}
	
	
	private List<User> getUsers(){
		
		if(users.size() == 0){
			users.add(getUser1());
			users.add(getUser2());
		}
		
		return users;
		
	}

}
