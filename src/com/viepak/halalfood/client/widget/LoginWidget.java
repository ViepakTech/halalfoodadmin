package com.viepak.halalfood.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.EventBus;
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
import com.google.gwt.user.client.ui.TextBox;
import com.viepak.halalfood.client.event.CreateUserEvent;
import com.viepak.halalfood.client.event.LoginUserEvent;
import com.viepak.halalfood.client.event.LogoutUserEvent;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.User;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.PasswordTextBox;

public class LoginWidget extends Composite implements Presenter {

	private HandlerManager eventBus;
	private UserManagementAsync userManagementService;
	private User user = null;
	private HasWidgets container;
	
	
	private static LoginWidgetUiBinder uiBinder = GWT
			.create(LoginWidgetUiBinder.class);
	@UiField TextBox txtEmail;
	@UiField PasswordTextBox txtPassword;
	@UiField Button btnLogin;
	@UiField AbsolutePanel LoginPanel;
	@UiField Button btnLogout;

	interface LoginWidgetUiBinder extends UiBinder<Widget, LoginWidget> {
	}

	public LoginWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public LoginWidget(HandlerManager eventBus, UserManagementAsync userManagementService){
		this.eventBus = eventBus;
		this.userManagementService = userManagementService;
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	@UiHandler("btnLogin")
	void onBtnLoginClick(ClickEvent event) {
		userManagementService.login(txtEmail.getText(), txtPassword.getText(), new AsyncCallback<User>() {
			
			@Override
			public void onSuccess(User result) {
				if(result != null){
					setUser(result);
					eventBus.fireEvent(new LoginUserEvent(result));
					txtEmail.setEnabled(false);
					txtPassword.setEnabled(false);
					btnLogin.setEnabled(false);
					btnLogout.setEnabled(true);
					unload();
				}
				else{
					Window.alert("Login Failed. Try again!!");
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Login Service is down. Try again later.");
			}
		});
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public void go(HasWidgets container) {
		this.container = container;
		container.clear();
		container.add(this.asWidget());
		
		System.out.println("Login widget loaded");
	}
	
	public void unload(){
		this.container.remove(this.asWidget());
	}
	
	@UiHandler("btnLogout")
	void onBtnLogoutClick(ClickEvent event) {
		txtEmail.setEnabled(true); txtEmail.setText("");
		txtPassword.setEnabled(true); txtPassword.setText("");
		btnLogin.setEnabled(true);
		btnLogout.setEnabled(false);
		eventBus.fireEvent(new LogoutUserEvent(null));
	}
}
