package com.viepak.halalfood.client.event;

import com.google.gwt.event.shared.GwtEvent;

public class UpdateUserEvent extends GwtEvent<UpdateUserEventHandler> {

	public static Type<UpdateUserEventHandler> TYPE = new Type<UpdateUserEventHandler>();
	
	private final String id;
	
	public UpdateUserEvent(String id){
		this.id = id;
	}
	
	@Override
	public com.google.gwt.event.shared.GwtEvent.Type<UpdateUserEventHandler> getAssociatedType() {
		return TYPE;
	}

	@Override
	protected void dispatch(UpdateUserEventHandler handler) {
		handler.onUpdateUser(this);
	}

	public String getId() {
		return id;
	}

}
