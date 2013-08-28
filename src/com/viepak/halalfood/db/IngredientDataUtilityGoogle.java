package com.viepak.halalfood.db;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.viepak.halalfood.shared.Ingredient;
import com.viepak.halalfood.shared.User;

public class IngredientDataUtilityGoogle {
	
	private static Entity Get(Ingredient ingredient, Entity ingredientEntity){
		ingredientEntity.setProperty("name", ingredient.getName());
		ingredientEntity.setProperty("eNumber", ingredient.geteNumber());
		ingredientEntity.setProperty("status", ingredient.getStatus());
		ingredientEntity.setProperty("evaluatedDate", ingredient.getEvaluatedDate());
		ingredientEntity.setProperty("evaluatedBy", ingredient.getEvaluatedBy());
		ingredientEntity.setProperty("remarks", ingredient.getRemarks());
		
		return ingredientEntity;
	}
	
	private static Ingredient Set(Entity result){
		Ingredient ingredient = new Ingredient();
		ingredient.setId((long)result.getKey().getId());
		ingredient.setName((String)result.getProperty("name"));
		ingredient.seteNumber((String)result.getProperty("eNumber"));
		ingredient.setStatus((String)result.getProperty("status"));
		ingredient.setEvaluatedDate((Date)result.getProperty("evaluatedDate"));
		ingredient.setEvaluatedBy((long)result.getProperty("evaluatedBy"));
		ingredient.setRemarks((String)result.getProperty("remarks"));
		
		return ingredient;
	}
	
	public static Ingredient syncIngredient(Ingredient ingredient){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		
		if(ingredient.getId() > 0){
			dataStore.put(Get(ingredient,new Entity("Ingredient", ingredient.getId())));
		}else{
			dataStore.put(Get(ingredient,new Entity("Ingredient")));
		}
		return ingredient;
	}
	
	public static List<Ingredient> GetAllIngredients(){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("Ingredient");
		PreparedQuery pq = dataStore.prepare(q);
		
		List<Ingredient> ingredients = new ArrayList<Ingredient>();
		
		for(Entity result: pq.asIterable()){
			ingredients.add(Set(result));
		}
		
		return ingredients;
	}

	public static boolean DeleteIngredient(long id){
		DatastoreService dataStore = DatastoreServiceFactory.getDatastoreService();
		dataStore.delete(new Entity("Ingredient", id).getKey());
		return true;
	} 

}
