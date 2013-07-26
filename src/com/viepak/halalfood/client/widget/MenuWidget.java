package com.viepak.halalfood.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.MenuItem;
import com.viepak.halalfood.client.event.LogoutUserEvent;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.InformationHub;
import com.viepak.halalfood.shared.User;

public class MenuWidget extends Composite {
	
	private HandlerManager eventBus;
	private UserManagementAsync userManagementService;
	private HasWidgets container;

	private static MenuWidgetUiBinder uiBinder = GWT
			.create(MenuWidgetUiBinder.class);
	@UiField MenuItem menuNewUser;
	@UiField MenuItem menuEditUser;
	@UiField MenuItem menuLogoutProfile;

	interface MenuWidgetUiBinder extends UiBinder<Widget, MenuWidget> {
	}

	public MenuWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public MenuWidget(HandlerManager eventBus, UserManagementAsync userManagementService){
		this.eventBus = eventBus;
		this.userManagementService =userManagementService;
		
		initWidget(uiBinder.createAndBindUi(this));
		
		iniEvents();
	}
	
	public void go(HasWidgets container){
		this.container = container;
		container.add(this.asWidget());
	}
	
	private void iniEvents(){
		menuLogoutProfile.setScheduledCommand(new ScheduledCommand() {
			
			@Override
			public void execute() {
				// TODO Auto-generated method stub
				userManagementService.logout(InformationHub.GetInstance().getUser(), new AsyncCallback<Boolean>() {
					
					@Override
					public void onSuccess(Boolean result) {
						// TODO Auto-generated method stub
						eventBus.fireEvent(new LogoutUserEvent(null));
					}
					
					@Override
					public void onFailure(Throwable caught) {
						// TODO Auto-generated method stub
						Window.alert("Logout failed");
					}
				});
			}
		});
	}

}
