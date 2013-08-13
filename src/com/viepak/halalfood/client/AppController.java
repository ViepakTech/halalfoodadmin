package com.viepak.halalfood.client;

import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.viepak.halalfood.client.event.LoginUserEvent;
import com.viepak.halalfood.client.event.LoginUserEventHandler;
import com.viepak.halalfood.client.service.IngredientManagementAsync;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.client.widget.LoginWidget;
import com.viepak.halalfood.client.widget.UserManagementWidget;
import com.viepak.halalfood.client.widget.MenuWidget;
import com.viepak.halalfood.client.widget.Presenter;
import com.viepak.halalfood.client.widget.TabWidget;
import com.viepak.halalfood.shared.InformationHub;

public class AppController implements Presenter, ValueChangeHandler<String> {

	private final UserManagementAsync userManagementService;
	private final IngredientManagementAsync ingredientManagementService;
	private final HandlerManager eventBus;
	private HasWidgets container;
	private final InformationHub infoHub;
	
	public AppController(UserManagementAsync userManagementService, IngredientManagementAsync ingredientManagementService, HandlerManager eventBus, InformationHub infoHub){
		this.eventBus = eventBus;
		this.userManagementService = userManagementService;
		this.ingredientManagementService = ingredientManagementService;
		this.infoHub = infoHub;
		bind();
	}
	
	private void bind() {
		History.addValueChangeHandler(this);
		
		/*eventBus.addHandler(LoginUserEvent.TYPE, new LoginUserEventHandler() {
			
			@Override
			public void onLoginUser(LoginUserEvent event) {
				// Globally assign value of user, so it can be accessible from anywhere in the app.
				InformationHub.GetInstance().setUser(event.user);
				doManagement();
			}
		});*/
	}
	
	private void doLoginUser(){
		History.newItem("Login");
	}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String token = event.getValue();
		Presenter presenter = null;
		
		if(token != null){
			if(token == "Main"){
				presenter = new TabWidget(eventBus, userManagementService, ingredientManagementService, infoHub);
			}
		}
			
		if(presenter != null){
			presenter.go(container);
		}
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		
		if("".equals(History.getToken()) || "Main".equals(History.getToken())){
			History.newItem("Main");
		}else{
			History.fireCurrentHistoryState();
		}
		
	}

}

