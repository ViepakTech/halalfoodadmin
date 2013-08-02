package com.viepak.halalfood.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.Window;
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
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Label;

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
	@UiField PasswordTextBox txtPassword;
	@UiField Label txtId;
	@UiField TextButton btnDelete;
	
	private HandlerManager eventBus;
	private UserManagementAsync userManagementService;
	private User user;

	interface CreateUserWidgetUiBinder extends UiBinder<Widget, CreateUserWidget> {
	}

	public CreateUserWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public CreateUserWidget(HandlerManager eventBus, UserManagementAsync userManagementService, User user){
		initWidget(uiBinder.createAndBindUi(this));
		
		this.eventBus = eventBus;
		this.userManagementService = userManagementService;
		this.user = user;
		
		lstRole.addItem(UserRole.SuperAdmin);
		lstRole.addItem(UserRole.Admin);
		
		initUI();
	}
	
	void initUI(){
		if(user != null){
			txtId.setText(user.getId() + "");
			txtName.setText(user.getName());
			txtEmail.setText(user.getEmail());
			txtPhone.setText(user.getPhoneNumber());
			txtPassword.setText(user.getPassword());
			chkActive.setValue(user.getIsActive());
			if(user.getRole().equals(UserRole.SuperAdmin)){
				lstRole.setItemSelected(0, true);
			}else{
				lstRole.setItemSelected(1, true);
			}
			btnDelete.setVisible(true);
		}else{
			btnDelete.setVisible(false);
		}
	}

	@UiHandler("btnSave")
	void onBtnSaveClick(ClickEvent event) {
		if(user == null) {
			user = new User();
		}
		user.setName(txtName.getText());
		user.setEmail(txtEmail.getText());
		user.setPhoneNumber(txtPhone.getText());
		user.setPassword(txtPassword.getText());
		user.setRole(lstRole.getValue(lstRole.getSelectedIndex()));
		user.setIsActive(chkActive.getValue());
		
		userManagementService.createUser(user, new AsyncCallback<User>() {
			
			@Override
			public void onSuccess(User result) {
				eventBus.fireEvent(new CreateUserEvent(result));
			}
			
			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Create user failed");
			}
		});
	}
	
	@UiHandler("btnCancel")
	void onBtnCancelClick(ClickEvent event) {
		eventBus.fireEvent(new CreateUserEvent(null));
	}
	@UiHandler("btnDelete")
	void onBtnDeleteClick(ClickEvent event) {
		userManagementService.deleteUser(user, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Failed to delete user");
			}

			@Override
			public void onSuccess(Boolean result) {
				eventBus.fireEvent(new CreateUserEvent(null));
			}
		});
	}
}
