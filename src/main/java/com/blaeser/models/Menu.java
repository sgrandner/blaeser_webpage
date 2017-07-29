package com.blaeser.models;

import java.util.ArrayList;
import java.util.List;

public class Menu {

	private List<MenuItem> menuItemList = new ArrayList<>();
	private Integer selectedPageId;

	public void addMenuItem(MenuItem menuItem) {

		menuItemList.add(menuItem);
	}

	public void clear() {

		menuItemList.clear();
	}

	public int size() {

		return menuItemList.size();
	}

	public List<MenuItem> getMenuItemList() {
		return menuItemList;
	}

	public void setMenuItemList(List<MenuItem> menuItemList) {
		this.menuItemList = menuItemList;
	}

	public Integer getSelectedPageId() {
		return selectedPageId;
	}

	public void setSelectedPageId(Integer selectedPageId) {
		this.selectedPageId = selectedPageId;
	}
}
