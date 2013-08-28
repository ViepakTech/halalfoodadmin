package com.viepak.halalfood.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.viepak.halalfood.shared.Ingredient;

public class EditIngredientEvent extends GwtEvent<EditIngredientEventHandler> {

	public static Type<EditIngredientEventHandler> TYPE = new Type<EditIngredientEventHandler>();
	public static Ingredient ingredient;
	
	public EditIngredientEvent(Ingredient ingredient){
		this.ingredient = ingredient;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<EditIngredientEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(EditIngredientEventHandler handler) {
		handler.onEditUser(this);
	}

}
