package com.viepak.halalfood.client.event;

import com.google.gwt.event.shared.EventHandler;

public interface LogoutUserEventHandler extends EventHandler {
	void onLogoutUser(LogoutUserEvent logoutUserEvent);
}
