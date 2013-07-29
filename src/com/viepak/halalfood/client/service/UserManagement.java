package com.viepak.halalfood.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.viepak.halalfood.shared.User;

/**
 * @author Nomi
 *
 */
@RemoteServiceRelativePath("usermanagementservice")
public interface UserManagement extends RemoteService {
	User createUser(User user);
	Boolean updateUser(User user);
	Boolean deleteUser(User user);
	List<User> getAllUser();
	User login(String userName, String password);
	Boolean logout(User user);
}
