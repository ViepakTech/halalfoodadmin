package com.viepak.halalfood.client.widget;

import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.ProvidesKey;
import com.viepak.halalfood.client.service.IngredientManagementAsync;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.Ingredient;
import com.viepak.halalfood.shared.User;
import com.google.gwt.user.cellview.client.CellTable;

public class IngredientManagementWidget extends Composite {

	private static IngredientManagementWidgetUiBinder uiBinder = GWT.create(IngredientManagementWidgetUiBinder.class);
	@UiField(provided=true) CellTable<Ingredient> ingredientCellTable = new CellTable<Ingredient>();
	private HandlerManager eventBus;
	private IngredientManagementAsync ingredientManagementService;
	private AsyncDataProvider<Ingredient> dataProvider = getIngredientData();
	
	private AsyncDataProvider<Ingredient> getIngredientData(){
		return new AsyncDataProvider<Ingredient>() {

			@Override
			protected void onRangeChanged(HasData<Ingredient> display) {
				final int start = display.getVisibleRange().getStart();
				ingredientManagementService.getAll(new AsyncCallback<List<Ingredient>>() {
					
					@Override
					public void onSuccess(List<Ingredient> result) {
						updateRowData(start, result);
						System.out.println("Ingredient's list loaded");
					}
					
					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Failed to get ingredient's list");
					}
				});
			}
		};
	}
	
	private static final ProvidesKey<Ingredient> KEY_PROVIDER = new ProvidesKey<Ingredient>() {
		@Override
		public Object getKey(Ingredient item) {
			return item.getId();
		}
	};
	
	private final Cell<String> idCell  = new TextCell();
	private final Cell<String> nameCell = new TextCell();
	private final Cell<String> eNumberCell = new TextCell();
	private final Cell<String> alternativeNamesCell = new TextCell();
	private final Cell<String> statusCell = new TextCell();
	private final Cell<String> evaluatedDateCell = new TextCell();
	private final Cell<String> evaluatedByCell = new TextCell();

	interface IngredientManagementWidgetUiBinder extends
			UiBinder<Widget, IngredientManagementWidget> {
	}

	public IngredientManagementWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public IngredientManagementWidget(HandlerManager eventBus, IngredientManagementAsync ingredientManagementService){
		initWidget(uiBinder.createAndBindUi(this));
		this.eventBus = eventBus;
		this.ingredientManagementService = ingredientManagementService;
	}

}
