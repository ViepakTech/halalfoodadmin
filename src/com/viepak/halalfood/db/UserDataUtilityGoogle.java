package com.viepak.halalfood.db;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
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
		user.setPassword((String)result.getProperty("password"));
		user.setPhoneNumber((String)result.getProperty("phoneNumber"));
		user.setIsActive((boolean)result.getProperty("IsActive"));
		
		return user;
	}
	
	private static Entity Get(User user){
		Entity userEntity = new Entity("User");
		userEntity.setProperty("name", user.getName());
		userEntity.setProperty("role", user.getRole());
		userEntity.setProperty("email", user.getEmail());
		userEntity.setProperty("password", user.getPassword());
		userEntity.setProperty("phoneNumber", user.getPhoneNumber());
		userEntity.setProperty("IsActive", user.getIsActive());
		
		return userEntity;
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
		dataStore.put(Get(user));
		return user;
		
	}
	
	public static User GetUserByFilter(Filter filter){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User").setFilter(filter);
		PreparedQuery pq = dataStore.prepare(q);
		User user = Set(pq.asSingleEntity());
		
		return user;
				
	}
	
	public static User GetUserByFilter(String email, String password){
		
		Filter emailFilter =  new FilterPredicate("email",FilterOperator.EQUAL,email);
		Filter passwordFilter = new FilterPredicate("password", FilterOperator.EQUAL, password);
		Filter combineFilter = CompositeFilterOperator.and(emailFilter, passwordFilter);
		return GetUserByFilter(combineFilter);
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
