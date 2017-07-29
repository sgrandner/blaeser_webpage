package com.blaeser.models;

public class MenuItem {

	private String name;
	private String label;
	private Integer pageId;
	private Boolean active;
	private Boolean selected;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Integer getPageId() {
		return pageId;
	}

	public void setPageId(Integer pageId) {
		this.pageId = pageId;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getSelected() {

		if(selected == null) {
			return false;
		}

		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}
}
