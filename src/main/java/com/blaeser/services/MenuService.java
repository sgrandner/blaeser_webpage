package com.blaeser.services;

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

		// TODO get pageId for menuName from db

		Menu menu = new Menu();

		menu.setId(1);
		menu.setLabel("Über uns");
		menu.setPageId(2);
		menu.setActive(true);

		return menu;
	}

}
