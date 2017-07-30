package com.blaeser.services;

import com.blaeser.models.ColumnType;
import com.blaeser.models.Menu;
import com.blaeser.models.MenuItem;

public class MenuService {

	public Menu createMenu(String selectedMenuName) {

		DbQuery dbQuery = new DbQuery();

		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("label", ColumnType.STRING);
		dbQuery.setColumnType("pageId", ColumnType.INT);
		dbQuery.setColumnType("active", ColumnType.BOOLEAN);

		dbQuery.query("selectActiveMenus");

		Menu menu = new Menu();
		Integer selectedPageId = null;

		while(dbQuery.readResults()) {

			MenuItem menuItem = new MenuItem();

			menuItem.setName(dbQuery.getValueAsString("name"));
			menuItem.setLabel(dbQuery.getValueAsString("label"));
			menuItem.setPageId(dbQuery.getValueAsInteger("pageId"));
			menuItem.setActive(dbQuery.getValueAsBoolean("active"));

			menu.addMenuItem(menuItem);

			if(menuItem.getName().equals(selectedMenuName)) {
				menuItem.setSelected(true);
				selectedPageId = menuItem.getPageId();
			}
		}

		if(menu.size() == 0) {
			return null;
		}

		menu.setSelectedPageId(selectedPageId);

		return menu;
	}

	public MenuItem createMenuItem(String menuName) {

		DbQuery dbQuery = new DbQuery();

		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("label", ColumnType.STRING);
		dbQuery.setColumnType("pageId", ColumnType.INT);
		dbQuery.setColumnType("active", ColumnType.BOOLEAN);

		dbQuery.query("selectMenuDataByMenuName", menuName);

		if(dbQuery.size() != 1) {
			// TODO log error
		}

		MenuItem menuItem = new MenuItem();

		while(dbQuery.readResults()) {

			menuItem.setName(dbQuery.getValueAsString("name"));
			menuItem.setLabel(dbQuery.getValueAsString("label"));
			menuItem.setPageId(dbQuery.getValueAsInteger("pageId"));
			menuItem.setActive(dbQuery.getValueAsBoolean("active"));
		}

		return menuItem;
	}
}
