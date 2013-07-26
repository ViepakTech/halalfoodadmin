package com.viepak.halalfood.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.viepak.halalfood.shared.User;

public class LoginUserEvent extends GwtEvent<LoginUserEventHandler> {

	public static Type<LoginUserEventHandler> TYPE = new Type<LoginUserEventHandler>();
	public User user;
	
	public LoginUserEvent(User user){
		this.user = user;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LoginUserEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(LoginUserEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onLoginUser(this);
	}

}
