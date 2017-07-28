package com.blaeser.services;

import com.blaeser.models.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageService {

	public Page createPage(Integer pageId) {

		if(pageId == null) {
			return null;
		}

		Page page = getPageData(pageId);

		if(page != null) {

			StringBuffer sb = new StringBuffer();
			sb.append(insertContent(page.getTemplate(), page.getImages(), page.getTexts()));

			page.setHtml(sb.toString());
		}

		return page;
	}

	public Page createPage(String pageName) {

		Integer pageId = getPageIdByPageName(pageName);

		if(pageId != null) {
			return createPage(pageId);
		}

		return null;
	}

	private Integer getPageIdByPageName(String pageName) {

		Integer pageId = null;

		try {

			DbQuery dbQuery = new DbQuery();

			dbQuery.setColumnType("id", ColumnType.INT);
			dbQuery.query("selectPageIdByPageName", pageName);

			while(dbQuery.readResults()) {

				pageId = dbQuery.getValueAsInteger("id");
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		return pageId;
	}

	private Page getPageData(int pageId) {

		Page page = new Page();
		DbQuery dbQuery = new DbQuery();

		// get page and template
		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("active", ColumnType.BOOLEAN);
		dbQuery.setColumnType("templateId", ColumnType.INT);
		dbQuery.setColumnType("creationDate", ColumnType.DATE);
		dbQuery.setColumnType("modifyDate", ColumnType.DATE);
		dbQuery.setColumnType("template", ColumnType.STRING);

		dbQuery.query("selectPageAndTemplateByPageId", pageId);

		while(dbQuery.readResults()) {
			page.setId(dbQuery.getValueAsInteger("id"));
			page.setName(dbQuery.getValueAsString("name"));
			page.setActive(dbQuery.getValueAsBoolean("active"));
			page.setTemplateId(dbQuery.getValueAsInteger("templateId"));
			page.setCreationDate(dbQuery.getValueAsDate("creationDate"));
			page.setModifyDate(dbQuery.getValueAsDate("modifyDate"));
			page.setTemplate(dbQuery.getValueAsString("template"));
		}

		// get images for page
		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("fileName", ColumnType.STRING);
		dbQuery.setColumnType("width", ColumnType.INT);
		dbQuery.setColumnType("height", ColumnType.INT);
		dbQuery.setColumnType("description", ColumnType.STRING);

		dbQuery.query("selectImagesByPageId", pageId);

		List<ContentImage> images = new ArrayList<ContentImage>();

		while(dbQuery.readResults()) {

			ContentImage image = new ContentImage();

			image.setId(dbQuery.getValueAsInteger("id"));
			image.setFileName(dbQuery.getValueAsString("fileName"));
			image.setWidth(dbQuery.getValueAsInteger("width"));
			image.setHeight(dbQuery.getValueAsInteger("height"));
			image.setDescription(dbQuery.getValueAsString("description"));

			images.add(image);
		}

		page.setImages(images);

		// get texts for page
		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("content", ColumnType.STRING);

		dbQuery.query("selectTextsByPageId", pageId);

		List<ContentText> texts = new ArrayList<ContentText>();

		while(dbQuery.readResults()) {

			ContentText text = new ContentText();

			text.setId(dbQuery.getValueAsInteger("id"));
			text.setContent(dbQuery.getValueAsString("content"));

			texts.add(text);
		}

		page.setTexts(texts);

		return page;
	}

	private String insertContent(String template, List<ContentImage> images, List<ContentText> texts) {

		StringBuffer sb = new StringBuffer();
		StringBuffer sbResult = new StringBuffer();

		sb.append(template);

		// add more patterns if needed (each pattern needs one numeric group (back reference))
		Map<Integer, Pattern> patternsMap = new HashMap<>();
		patternsMap.put(0, Pattern.compile("\\{text([0-9])+\\.([a-zA-Z0-9_-]+)}"));
		patternsMap.put(1, Pattern.compile("\\{image([0-9])+\\.([a-zA-Z0-9_-]+)}"));

		for(Integer key : patternsMap.keySet()) {

			Matcher matcher = patternsMap.get(key).matcher(sb);

			while(matcher.find()) {

				try {
					final int index = Integer.parseInt(matcher.group(1));
					final String parameter = matcher.group(2);
					switch (key) {
						case 0:
							switch (parameter) {
								case "content":
									matcher.appendReplacement(sbResult, texts.get(index - 1).getContent());
									break;
							}
							break;
						case 1:
							switch (parameter) {
								case "fileName":
									matcher.appendReplacement(sbResult, images.get(index - 1).getFileName());
									break;
								case "width":
									matcher.appendReplacement(sbResult, Integer.toString(images.get(index - 1).getWidth()));
									break;
								case "height":
									matcher.appendReplacement(sbResult, Integer.toString(images.get(index - 1).getHeight()));
									break;
								case "description":
									matcher.appendReplacement(sbResult, images.get(index - 1).getDescription());
									break;
							}
							break;
					}
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			matcher.appendTail(sbResult);

			// TODO check this -> sbResult as temporary stringbuffer needed ?
			sb.setLength(0);
			sb.append(sbResult);
			sbResult.setLength(0);
		}

		return sb.toString();
	}
}
