package com.blaeser.services;

import com.blaeser.models.ColumnType;
import com.blaeser.models.Menu;

import java.util.HashMap;
import java.util.Map;

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
		Map<String, ColumnType> columnTypeMap = new HashMap<>();

		columnTypeMap.clear();
		columnTypeMap.put("id", ColumnType.INT);
		columnTypeMap.put("label", ColumnType.STRING);
		columnTypeMap.put("pageId", ColumnType.INT);
		columnTypeMap.put("active", ColumnType.BOOLEAN);

		dbQuery.executeStatement("SELECT m.id, m.label, m.pageId, m.active " +
				"FROM menu AS m " +
				"WHERE m.name = '" + menuName + "'", columnTypeMap);

		for(Map<String, Object> rowMap : dbQuery.getResultList()) {

			menu.setId((Integer)rowMap.get("id"));
			menu.setLabel((String)rowMap.get("label"));
			menu.setPageId((Integer)rowMap.get("pageId"));
			menu.setActive((Boolean)rowMap.get("active"));
		}

		return menu;
	}

}
