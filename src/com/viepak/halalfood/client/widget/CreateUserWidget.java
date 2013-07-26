package com.viepak.halalfood.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.SimpleCheckBox;
import com.google.gwt.widget.client.TextButton;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.viepak.halalfood.client.event.CreateUserEvent;
import com.viepak.halalfood.client.event.CreateUserEventHandler;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.User;
import com.viepak.halalfood.shared.UserRole;

public class CreateUserWidget extends Composite {

	private static CreateUserWidgetUiBinder uiBinder = GWT
			.create(CreateUserWidgetUiBinder.class);
	@UiField TextBox txtName;
	@UiField ListBox lstRole;
	@UiField TextBox txtEmail;
	@UiField TextBox txtPhone;
	@UiField SimpleCheckBox chkActive;
	@UiField TextButton btnSave;
	@UiField TextButton btnCancel;
	
	private HandlerManager eventBus;
	private UserManagementAsync userManagementService;

	interface CreateUserWidgetUiBinder extends UiBinder<Widget, CreateUserWidget> {
	}

	public CreateUserWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public CreateUserWidget(HandlerManager eventBus, UserManagementAsync userManagementService){
		initWidget(uiBinder.createAndBindUi(this));
		
		this.eventBus = eventBus;
		this.userManagementService = userManagementService;
		
		lstRole.addItem(UserRole.SuperAdmin.toString());
		lstRole.addItem(UserRole.Admin.toString());
	}

	@UiHandler("btnSave")
	void onBtnSaveClick(ClickEvent event) {
		User user = new User();
		user.setName(txtName.getText());
		user.setEmail(txtEmail.getText());
		user.setPhoneNumber(txtPhone.getText());
		//user.setRole((UserRole)lstRole.getValue(lstRole.getSelectedIndex()));
		user.setIsActive(chkActive.getValue());
		
		userManagementService.createUser(user, new AsyncCallback<User>() {
			
			@Override
			public void onSuccess(User result) {
				eventBus.fireEvent(new CreateUserEvent(result));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	@UiHandler("btnCancel")
	void onBtnCancelClick(ClickEvent event) {
		eventBus.fireEvent(new CreateUserEvent(null));
	}
}
