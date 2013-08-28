package com.viepak.halalfood.server;

import java.util.List;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.viepak.halalfood.client.service.IngredientManagement;
import com.viepak.halalfood.db.IngredientDataUtilityGoogle;
import com.viepak.halalfood.shared.Ingredient;

public class IngredientManagementImpl extends RemoteServiceServlet implements IngredientManagement {

	@Override
	public Ingredient create(Ingredient ingredient) {
		return IngredientDataUtilityGoogle.syncIngredient(ingredient);
	}

	@Override
	public Ingredient update(Ingredient ingredient) {
		return IngredientDataUtilityGoogle.syncIngredient(ingredient);
	}

	@Override
	public boolean delete(Ingredient ingredient) {
		return IngredientDataUtilityGoogle.DeleteIngredient(ingredient.getId());
	}

	@Override
	public List<Ingredient> getAll() {
		return IngredientDataUtilityGoogle.GetAllIngredients();
	}

	@Override
	public Ingredient get(Ingredient ingredient) {
		// TODO Auto-generated method stub
		return null;
	}

}
