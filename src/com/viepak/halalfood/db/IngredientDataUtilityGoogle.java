package com.viepak.halalfood.db;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.viepak.halalfood.shared.Ingredient;

public class IngredientDataUtilityGoogle {
	
	private static Entity Get(Ingredient ingredient, Entity ingredientEntity){
		/*userEntity.setProperty("name", user.getName());
		userEntity.setProperty("role", user.getRole());
		userEntity.setProperty("email", user.getEmail());
		userEntity.setProperty("password", user.getPassword());
		userEntity.setProperty("phoneNumber", user.getPhoneNumber());
		userEntity.setProperty("IsActive", user.getIsActive());*/
		
		ingredientEntity.setProperty("", ingredient.geteNumber());
		
		return ingredientEntity;
	}
	
	public static Ingredient SyncUser(Ingredient ingredient){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		
		if(ingredient.getId() > 0){
			dataStore.put(Get(ingredient,new Entity("User", ingredient.getId())));
		}else{
			dataStore.put(Get(ingredient,new Entity("User")));
		}
		return ingredient;
	}
}
