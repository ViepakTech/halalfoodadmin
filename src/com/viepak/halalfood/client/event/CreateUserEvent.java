package com.viepak.halalfood.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.viepak.halalfood.shared.User;

public class CreateUserEvent extends GwtEvent<CreateUserEventHandler> {
	
	public static Type<CreateUserEventHandler> TYPE = new Type<CreateUserEventHandler>();
	public static User user;
	public CreateUserEvent(User user){
		this.user = user;
	}
	
	@Override
	public Type<CreateUserEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(CreateUserEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onCreateUser(this);
	}

}
