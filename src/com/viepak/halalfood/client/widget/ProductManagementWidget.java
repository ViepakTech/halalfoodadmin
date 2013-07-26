package com.viepak.halalfood.client.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ProductManagementWidget extends Composite {

	private static ProductManagementWidgetUiBinder uiBinder = GWT
			.create(ProductManagementWidgetUiBinder.class);

	interface ProductManagementWidgetUiBinder extends
			UiBinder<Widget, ProductManagementWidget> {
	}

	public ProductManagementWidget() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
