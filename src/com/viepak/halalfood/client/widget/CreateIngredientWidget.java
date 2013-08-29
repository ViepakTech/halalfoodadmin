package com.viepak.halalfood.client.widget;

import java.io.Console;
import java.text.ParseException;
import java.util.Date;

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
import com.viepak.halalfood.client.event.EditIngredientEvent;
import com.viepak.halalfood.client.service.IngredientManagementAsync;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.Ingredient;
import com.viepak.halalfood.shared.IngredientStatus;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.ibm.icu.text.SimpleDateFormat;

public class CreateIngredientWidget extends Composite {

	private static CreateIngredientWidgetUiBinder uiBinder = GWT
			.create(CreateIngredientWidgetUiBinder.class);
	@UiField TextBox txtName;
	@UiField TextBox txtENumber;
	@UiField TextArea txtAlternativeNames;
	@UiField TextButton btnSave;
	@UiField TextButton btnCancel;
	@UiField ListBox lstStatus;
	@UiField Label txtId;
	@UiField TextButton btnDelete;
	@UiField TextBox txtEvalutedDate;
	@UiField TextBox txtEvaluatedBy;
	
	private HandlerManager eventBus;
	private IngredientManagementAsync ingredientManagementService;
	private Ingredient ingredient;

	interface CreateIngredientWidgetUiBinder extends UiBinder<Widget, CreateIngredientWidget> {
	}

	public CreateIngredientWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public CreateIngredientWidget(HandlerManager eventBus, IngredientManagementAsync ingredientManagementService, Ingredient ingredient){
		initWidget(uiBinder.createAndBindUi(this));
		
		this.eventBus = eventBus;
		this.ingredientManagementService = ingredientManagementService;
		this.ingredient = ingredient;
		
		lstStatus.addItem(IngredientStatus.Halal);
		lstStatus.addItem(IngredientStatus.Haraam);
		lstStatus.addItem(IngredientStatus.Mushbooh);
		lstStatus.addItem(IngredientStatus.Pending);
		
		initUI();
	}
	
	void initUI(){
		if(ingredient != null){
			txtId.setText(ingredient.getId() + "");
			txtName.setText(ingredient.getName());
			txtAlternativeNames.setText(ingredient.getAlternativeNames());
			txtENumber.setText(ingredient.geteNumber());
			
			if(ingredient.getStatus() != null){
				if(ingredient.getStatus().equals(IngredientStatus.Halal)){
					lstStatus.setItemSelected(0, true);
				}else if(ingredient.getStatus().equals(IngredientStatus.Haraam)){
					lstStatus.setItemSelected(1, true);
				}else if(ingredient.getStatus().equals(IngredientStatus.Mushbooh)){
					lstStatus.setItemSelected(2, true);
				}else if(ingredient.getStatus().equals(IngredientStatus.Pending)){
					lstStatus.setItemSelected(3, true);
				}
			}
			
			btnDelete.setVisible(true);
		}else{
			btnDelete.setVisible(false);
		}
	}

	@UiHandler("btnSave")
	void onBtnSaveClick(ClickEvent event) {
		if(ingredient == null) {
			ingredient = new Ingredient();
		}
		ingredient.setName(txtName.getText());
		ingredient.seteNumber(txtENumber.getText());
		ingredient.setAlternativeNames(txtAlternativeNames.getText());
		ingredient.setEvaluatedBy(Long.parseLong(lstStatus.getValue(lstStatus.getSelectedIndex())));
		try {
			ingredient.setEvaluatedDate(new SimpleDateFormat("mm/dd/yyyy").parse(txtEvalutedDate.getText()));
		} catch (ParseException e) {
			System.out.println("date converstion failed");
		}
		
		ingredientManagementService.create(ingredient, new AsyncCallback<Ingredient>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to create ingredient");
			}

			@Override
			public void onSuccess(Ingredient result) {
				eventBus.fireEvent(new EditIngredientEvent(result));
			}
		});
	}
	
	@UiHandler("btnCancel")
	void onBtnCancelClick(ClickEvent event) {
		eventBus.fireEvent(new EditIngredientEvent(null));
	}
	@UiHandler("btnDelete")
	void onBtnDeleteClick(ClickEvent event) {
		
		ingredientManagementService.delete(ingredient, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Unable to delete Ingredient");
			}

			@Override
			public void onSuccess(Boolean result) {
				eventBus.fireEvent(new EditIngredientEvent(null));
			}
		});
	}
}
