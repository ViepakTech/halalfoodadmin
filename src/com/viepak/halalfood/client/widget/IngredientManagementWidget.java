package com.viepak.halalfood.client.widget;

import java.util.List;

import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
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
import com.google.gwt.view.client.SingleSelectionModel;
import com.viepak.halalfood.client.service.IngredientManagementAsync;
import com.viepak.halalfood.client.service.UserManagementAsync;
import com.viepak.halalfood.shared.Ingredient;
import com.viepak.halalfood.shared.User;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.Column;

public class IngredientManagementWidget extends Composite {

	private static IngredientManagementWidgetUiBinder uiBinder = GWT.create(IngredientManagementWidgetUiBinder.class);
	@UiField(provided=true) CellTable<Ingredient> ingredientCellTable = new CellTable<Ingredient>(KEY_PROVIDER);
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
	
	private final Column<Ingredient, String> idColumn = new Column<Ingredient, String>(idCell) {
		
		@Override
		public String getValue(Ingredient object) {
			return object.getId() + "";
		}
	};
	
	private final Column<Ingredient, String> nameColumn = new Column<Ingredient, String>(nameCell) {
		
		@Override
		public String getValue(Ingredient object) {
			return object.getName();
		}
	};
	
	private final Column<Ingredient, String> eNumberColumn = new Column<Ingredient, String>(eNumberCell) {
		
		@Override
		public String getValue(Ingredient object) {
			return object.geteNumber();
		}
	};
	
	private final Column<Ingredient, String> alternativeNamesColumn = new Column<Ingredient, String>(alternativeNamesCell) {
		
		@Override
		public String getValue(Ingredient object) {
			return object.getAlternativeNames();
		}
	};
	
	private final Column<Ingredient, String> statusColumn = new Column<Ingredient, String>(statusCell) {
		
		@Override
		public String getValue(Ingredient object) {
			return object.getStatus();
		}
	};
	
	private final Column<Ingredient, String> evaluatedDateColumn = new Column<Ingredient, String>(evaluatedDateCell) {
		
		@Override
		public String getValue(Ingredient object) {
			return object.getEvaluatedDate().toString();
		}
	};
	
	private final Column<Ingredient, String> evaluatedByColumn = new Column<Ingredient, String>(evaluatedByCell) {
		
		@Override
		public String getValue(Ingredient object) {
			return object.getEvaluatedBy() + "";
		}
	};

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
	
	private void initDataTable(){
		ingredientCellTable.addColumn(idColumn, "ID");
		ingredientCellTable.addColumn(nameColumn, "Name");
		ingredientCellTable.addColumn(eNumberColumn, "eNumber");
		ingredientCellTable.addColumn(alternativeNamesColumn, "Alternative Names");
		ingredientCellTable.addColumn(statusColumn, "Status");
		ingredientCellTable.addColumn(evaluatedDateColumn, "Evaluation Date");
		ingredientCellTable.addColumn(evaluatedByColumn, "Evaluated By");
		
		dataProvider.addDataDisplay(ingredientCellTable);
		
		final SingleSelectionModel<Ingredient> selectionModel = new SingleSelectionModel<Ingredient>();
		ingredientCellTable.setSelectionModel(selectionModel);
		ingredientCellTable.addDomHandler(new DoubleClickHandler() {
			
			@Override
			public void onDoubleClick(DoubleClickEvent event) {
				Ingredient selectedIngredient = selectionModel.getSelectedObject();
				
			}
		}, DoubleClickEvent.getType());
		
	}

	@UiHandler("btnAddNewIngredient")
	void onBtnAddNewIngredientClick(ClickEvent event) {
		
	}
}
