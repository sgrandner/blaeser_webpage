package com.blaeser.services;

import com.blaeser.models.ColumnType;
import com.blaeser.models.Menu;

public class MenuService {

	public Menu createMenu(String menuName) {

		Menu menu = getMenuData(menuName);

		if(menu != null) {

			StringBuffer sb = new StringBuffer();
			// TODO
			sb.append("Html des Menüs...");

			menu.setHtml(sb.toString());
		}

		return menu;
	}

	private Menu getMenuData(String menuName) {

		Menu menu = new Menu();
		DbQuery dbQuery = new DbQuery();

		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("label", ColumnType.STRING);
		dbQuery.setColumnType("pageId", ColumnType.INT);
		dbQuery.setColumnType("active", ColumnType.BOOLEAN);

		dbQuery.query("selectMenuDataByMenuName", menuName);

		while(dbQuery.readResults()) {

			menu.setId(dbQuery.getValueAsInteger("id"));
			menu.setLabel(dbQuery.getValueAsString("label"));
			menu.setPageId(dbQuery.getValueAsInteger("pageId"));
			menu.setActive(dbQuery.getValueAsBoolean("active"));
		}

		return menu;
	}

}
