package com.viepak.halalfood.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.CheckboxCell;
import com.google.gwt.cell.client.NumberCell;
import com.google.gwt.cell.client.SelectionCell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.TextInputCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.viepak.halalfood.client.event.CreateUserEvent;
import com.viepak.halalfood.client.event.CreateUserEventHandler;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.InformationHub;
import com.viepak.halalfood.shared.User;
import com.viepak.halalfood.shared.UserRole;

public class UserManagementWidget extends Composite implements Presenter {

	private static ManageUserWidgetUiBinder uiBinder = GWT.create(ManageUserWidgetUiBinder.class);
	private HandlerManager eventBus;
	private UserManagementAsync userManagementService;
	private List<User> userList;
	private DialogBox newUserDialog = new DialogBox();
	private AsyncDataProvider<User> dataProvider = getUserData(); 	
	private CreateUserWidget createUserWidget;
	
	private AsyncDataProvider<User> getUserData(){
		return new AsyncDataProvider<User>() {
			
			@Override
			protected void onRangeChanged(HasData<User> display) {
				final int start = display.getVisibleRange().getStart();
				userManagementService.getAllUser(new AsyncCallback<List<User>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Failed to get the users list");
					}

					@Override
					public void onSuccess(List<User> result) {
						userList = result;
						updateRowData(start, result);
						System.out.println("Users list loaded");
					}
				});
				
			}
		};

	}
	
	private static final ProvidesKey<User> KEY_PROVIDER = new ProvidesKey<User>() {
		@Override
		public Object getKey(User item) {
			return item.getId();
		}
	};
	
	private List<String> getRoleValues(){
		List<String> roleValues = new ArrayList<String>();
		roleValues.add("SuperAdmin");
		roleValues.add("Admin");
		return roleValues;
	}
	
	private final Cell<String> idCell  = new TextCell();
	private final TextInputCell nameCell = new TextInputCell();
	private final SelectionCell roleCell = new SelectionCell(getRoleValues());
	private final TextInputCell emailCell = new TextInputCell();
	private final TextInputCell phoneNumberCell = new TextInputCell();
	private final CheckboxCell isActiveCell = new CheckboxCell();
	
	private final Column<User, String> idColumn = new Column<User, String>(idCell) {
		
		@Override
		public String getValue(User object) {
			return object.getId() + "";
		}
	};
	
	private final Column<User, String> nameColumn = new Column<User, String>(nameCell) {
		@Override
		public String getValue(User object) {
			return object.getName();
		}
	};
	private final Column<User, String> roleColumn = new Column<User, String>(roleCell) {
		
		@Override
		public String getValue(User object) {
			return object.getRole().toString();
		}
	};
	private final Column<User, String> emailColumn = new Column<User, String>(emailCell) {

		@Override
		public String getValue(User object) {
			return object.getEmail();
		}
	};
	private final Column<User, String> phoneNumberColumn = new Column<User, String>(phoneNumberCell){

		@Override
		public String getValue(User object) {
			// TODO Auto-generated method stub
			return object.getPhoneNumber();
		}
		
	};
	private final Column<User, Boolean> isActiveColumn = new Column<User, Boolean>(isActiveCell) {
		
		@Override
		public Boolean getValue(User object) {
			// TODO Auto-generated method stub
			return object.getIsActive();
		}
	};
	
	@UiField(provided=true) CellTable<User> userCellTable = new CellTable<User>(KEY_PROVIDER);
	@UiField Button btnAddNewUser;

	interface ManageUserWidgetUiBinder extends
			UiBinder<Widget, UserManagementWidget> {
	}

	public UserManagementWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	public UserManagementWidget(HandlerManager eventBus, UserManagementAsync userManagementService) {
		initWidget(uiBinder.createAndBindUi(this));
		
		this.eventBus = eventBus;
		this.userManagementService = userManagementService;
		createUserWidget = new CreateUserWidget(eventBus, userManagementService);
		
		iniDatagrid();
		
		this.eventBus.addHandler(CreateUserEvent.TYPE, new CreateUserEventHandler() {
			
			@Override
			public void onCreateUser(CreateUserEvent event) {
				newUserDialog.hide();
				newUserDialog.remove(createUserWidget);
				userCellTable.setVisibleRangeAndClearData(userCellTable.getVisibleRange(), true);
			}
		});
	}
	
	private void iniDatagrid(){
		userCellTable.addColumn(idColumn, "ID");
		userCellTable.addColumn(nameColumn, "Name");
		userCellTable.addColumn(roleColumn, "Role");
		userCellTable.addColumn(emailColumn, "Email");
		userCellTable.addColumn(phoneNumberColumn, "Phone #");
		userCellTable.addColumn(isActiveColumn, "Active");
		
		dataProvider.addDataDisplay(userCellTable);
	}

	@Override
	public void go(HasWidgets container) {
		container.add(this.asWidget());
	}

	@UiHandler("btnAddNewUser")
	void onBtnAddNewUserClick(ClickEvent event) {
		newUserDialog.add(createUserWidget);
		newUserDialog.center();
		newUserDialog.show();
	}
}



