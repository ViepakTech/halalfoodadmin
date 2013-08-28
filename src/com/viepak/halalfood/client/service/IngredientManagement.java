package com.viepak.halalfood.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.viepak.halalfood.shared.Ingredient;

@RemoteServiceRelativePath("ingredientmanagementservice")
public interface IngredientManagement extends RemoteService {
	Ingredient create(Ingredient  ingredient);
	Ingredient update(Ingredient ingredient);
	boolean delete(Ingredient ingredient);
	List<Ingredient> getAll();
	Ingredient get(Ingredient ingredient);
}
