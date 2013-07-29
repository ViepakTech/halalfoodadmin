package com.viepak.halalfood.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.viepak.halalfood.shared.User;
import com.viepak.halalfood.shared.UserRole;

/*
 * private long Id;
	
	private String name;
	private UserRole role;
	private String email;
	private String phoneNumber;
	private Boolean IsActive;
 * 
 * 
 */

public class UserDataUtilityGoogle {
	
	private static User Set(Entity result){
		User user = new User();
		user.setId((long)result.getKey().getId());
		user.setName((String)result.getProperty("name"));
		user.setRole((String)result.getProperty("role"));
		user.setEmail((String)result.getProperty("email"));
		user.setPhoneNumber((String)result.getProperty("phoneNumber"));
		user.setIsActive((boolean)result.getProperty("IsActive"));
		
		return user;
	}
	
	public static List<User> GetAllUsers(){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User");
		PreparedQuery pq = dataStore.prepare(q);
		
		List<User> users = new ArrayList<User>();
		
		for(Entity result: pq.asIterable()){
			users.add(Set(result));
		}
		
		return users;
	}
	
	public static User CreateUser(User user){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Entity userEntity = new Entity("User");
		userEntity.setProperty("name", user.getName());
		userEntity.setProperty("role", user.getRole());
		userEntity.setProperty("email", user.getEmail());
		userEntity.setProperty("phoneNumber", user.getPhoneNumber());
		userEntity.setProperty("IsActive", user.getIsActive());
		dataStore.put(userEntity);
		
		return user;
		
	}
	
	public static boolean UpdateUser(User user){
		EntityManager em = EMF.get().createEntityManager();
		try{
			em.persist(user);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return false;
		}finally{
			em.close();
		}
	}
	
	public static boolean DeleteUser(long id){
		EntityManager em = EMF.get().createEntityManager();
		try{
			User user = em.find(User.class, id);
			em.remove(user);
			return true;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			return false;
		}finally{
			em.close();
		}
	} 
}
