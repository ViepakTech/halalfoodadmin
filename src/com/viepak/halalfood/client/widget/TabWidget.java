package com.viepak.halalfood.client.widget;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TabPanel;
import com.viepak.halalfood.client.event.LoginUserEvent;
import com.viepak.halalfood.client.event.LoginUserEventHandler;
import com.viepak.halalfood.client.event.LogoutUserEvent;
import com.viepak.halalfood.client.event.LogoutUserEventHandler;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.InformationHub;

public class TabWidget extends Composite implements Presenter {

	private HandlerManager eventBus;
	private UserManagementAsync userManagementService;
	private InformationHub infoHub;
	private LoginWidget loginWidget;
	private UserManagementWidget manageUserWidget;
	private ProductManagementWidget manageProductWidget;
	
	private static TabWidgetUiBinder uiBinder = GWT
			.create(TabWidgetUiBinder.class);
	@UiField TabPanel tabPanelControl = new TabPanel();

	interface TabWidgetUiBinder extends UiBinder<Widget, TabWidget> {
	}

	public TabWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public TabWidget(HandlerManager eventBus, UserManagementAsync userManagementService, InformationHub infoHub){
		initWidget(uiBinder.createAndBindUi(this));
		
		this.eventBus = eventBus;
		this.userManagementService = userManagementService;
		this.infoHub = infoHub;
		
		loginWidget = new LoginWidget(eventBus, userManagementService);
		tabPanelControl.add(loginWidget, "Login");
		
		tabPanelControl.selectTab(getAbsoluteLeft());
		tabPanelControl.setAnimationEnabled(true);
	}

	@Override
	public void go(HasWidgets container) {
		container.add(this.asWidget());
		
		eventBus.addHandler(LoginUserEvent.TYPE, new LoginUserEventHandler() {
			
			@Override
			public void onLoginUser(LoginUserEvent event) {
				infoHub.GetInstance().setUser(event.user);
				doEnableManagement();
			}
		});
		
		eventBus.addHandler(LogoutUserEvent.TYPE, new LogoutUserEventHandler() {
			
			@Override
			public void onLogoutUser(LogoutUserEvent logoutUserEvent) {
				infoHub.GetInstance().setUser(null);
				doDisableManagement();
				
			}
		});
	}
	
	private void doEnableManagement(){
		if(manageUserWidget == null){
			manageUserWidget = new UserManagementWidget(eventBus, userManagementService);
		}
		if(manageProductWidget == null){
			manageProductWidget = new ProductManagementWidget();
		}
		tabPanelControl.add(manageUserWidget, "User Management");
		tabPanelControl.add(manageProductWidget, "Product Management");
	}
	
	private void doDisableManagement(){
		tabPanelControl.remove(manageUserWidget);
		manageUserWidget = null;
		
		tabPanelControl.remove(manageProductWidget);
		manageProductWidget = null;
	}
}
