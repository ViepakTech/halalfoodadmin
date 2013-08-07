package com.viepak.halalfood.client.service;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.viepak.halalfood.shared.Ingredient;

public interface IngredientManagementAsync {

	void create(Ingredient ingredient, AsyncCallback<Ingredient> callback);

	void delete(Ingredient ingredient, AsyncCallback<Boolean> callback);

	void get(Ingredient ingredient, AsyncCallback<Ingredient> callback);

	void getAll(AsyncCallback<List<Ingredient>> callback);

	void update(Ingredient ingredient, AsyncCallback<Ingredient> callback);

}
