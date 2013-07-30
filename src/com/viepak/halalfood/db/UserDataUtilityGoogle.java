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
	
	private static Entity Get(User user, boolean update){
		Entity userEntity = new Entity("User");
		if(update){
			userEntity.setProperty("ID", user.getId());
		}
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
		dataStore.put(Get(user, false));
		return user;
	}
	
	public static Entity GetEntityByFilter(Filter filter){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User").setFilter(filter);
		PreparedQuery pq = dataStore.prepare(q);
		return pq.asSingleEntity();
	}
	
	public static User GetUserByFilter(Filter filter){
		return Set(GetEntityByFilter(filter));
	}
	
	public static User GetUserByFilter(String email, String password){
		Filter emailFilter =  new FilterPredicate("email",FilterOperator.EQUAL,email);
		Filter passwordFilter = new FilterPredicate("password", FilterOperator.EQUAL, password);
		Filter combineFilter = CompositeFilterOperator.and(emailFilter, passwordFilter);
		return GetUserByFilter(combineFilter);
	}
	
	public static User GetUserByFilter(double Id){
		Filter idFilter = new FilterPredicate("ID", FilterOperator.EQUAL, Id);
		return GetUserByFilter(idFilter);
	}
	
	public static boolean UpdateUser(User user){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		dataStore.put(Get(user,true));
		return true;
	}
	
	public static boolean DeleteUser(long id){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Filter idFilter = new FilterPredicate("ID", FilterOperator.EQUAL, id);
		dataStore.delete(GetEntityByFilter(idFilter).getKey());
		return true;
	} 
}
