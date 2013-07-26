package com.viepak.halalfood.client.event;

import com.google.gwt.event.shared.GwtEvent;
import com.viepak.halalfood.shared.User;

public class LogoutUserEvent extends GwtEvent<LogoutUserEventHandler> {

	public static Type<LogoutUserEventHandler> TYPE = new Type<LogoutUserEventHandler>();
	public User user;
	
	public LogoutUserEvent(User user){
		this.user = user;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<LogoutUserEventHandler> getAssociatedType() {
		// TODO Auto-generated method stub
		return TYPE;
	}

	@Override
	protected void dispatch(LogoutUserEventHandler handler) {
		// TODO Auto-generated method stub
		handler.onLogoutUser(this);
	}

}
