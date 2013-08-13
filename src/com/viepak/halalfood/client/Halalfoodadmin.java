package com.viepak.halalfood.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.ui.RootPanel;
import com.viepak.halalfood.client.service.IngredientManagement;
import com.viepak.halalfood.client.service.IngredientManagementAsync;
import com.viepak.halalfood.client.service.UserManagement;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.InformationHub;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Halalfoodadmin implements EntryPoint {

	public void onModuleLoad() {
		UserManagementAsync userManagementService = GWT.create(UserManagement.class);
		IngredientManagementAsync ingredientManagementService = GWT.create(IngredientManagement.class);
		HandlerManager eventBus = new HandlerManager(null);
		AppController appController = new AppController(userManagementService, ingredientManagementService, eventBus, InformationHub.GetInstance());
		appController.go(RootPanel.get("applicationArea"));
	}
}
