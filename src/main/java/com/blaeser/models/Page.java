package com.blaeser.models;

import java.util.Date;
import java.util.List;

public class Page {

	private int id;
	private String name;
	private Boolean active;
	private int templateId;
	private Date creationDate;
	private Date modifyDate;

	private String template;
	private List<ContentImage> images;
	private List<ContentText> texts;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public int getTemplateId() {
		return templateId;
	}

	public void setTemplateId(int templateId) {
		this.templateId = templateId;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/* -------------- */
	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<ContentImage> getImages() {
		return images;
	}

	public void setImages(List<ContentImage> images) {
		this.images = images;
	}

	public List<ContentText> getTexts() {
		return texts;
	}

	public void setTexts(List<ContentText> texts) {
		this.texts = texts;
	}
}
