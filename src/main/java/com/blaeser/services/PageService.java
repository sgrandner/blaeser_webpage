package com.blaeser.services;

import com.blaeser.models.*;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.xml.bind.*;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

			URL url = PageService.class.getResource("/sql/sql_queries.xml");
			File file = new File(url.getFile());

			// TODO make singleton class for sqlTemplates and get instance in DbQuery
			// Java Architecture for XML Binding (JAXB)
			JAXBContext jaxbContext = JAXBContext.newInstance("com.blaeser.models");

			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			JAXBElement<SqlTemplateGroupType> sqlTemplateGroup = (JAXBElement<SqlTemplateGroupType>)unmarshaller.unmarshal(file);

			Map<String, String> sqlTemplateMap = new HashMap<>();

			for(SqlTemplateType sqlTemplate : sqlTemplateGroup.getValue().getSqlTemplate()) {

				sqlTemplateMap.put(sqlTemplate.getName(), sqlTemplate.getValue());
			}

			DbQuery dbQuery = new DbQuery();
			Map<String, ColumnType> columnTypeMap = new HashMap<>();
			columnTypeMap.clear();
			columnTypeMap.put("id", ColumnType.INT);

			// TODO check page.active here as well !?!

			Matcher matcher = Pattern.compile("\\?").matcher(sqlTemplateMap.get("selectPageIdByPageName"));

			StringBuffer processedSqlTemplate = new StringBuffer();
			while(matcher.find()) {

				try {
					matcher.appendReplacement(processedSqlTemplate, pageName);
				}
				catch (Exception ex) {
					ex.printStackTrace();
				}

				matcher.appendTail(processedSqlTemplate);
			}

			dbQuery.executeStatement(processedSqlTemplate.toString(), columnTypeMap);

			for(Map<String, Object> rowMap : dbQuery.getResultList()) {
				pageId = (Integer)rowMap.get("id");
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
		Map<String, ColumnType> columnTypeMap = new HashMap<>();

		// get page data
		columnTypeMap.clear();
		columnTypeMap.put("id", ColumnType.INT);
		columnTypeMap.put("name", ColumnType.STRING);
		columnTypeMap.put("active", ColumnType.BOOLEAN);
		columnTypeMap.put("templateId", ColumnType.INT);
		columnTypeMap.put("creationDate", ColumnType.DATE);
		columnTypeMap.put("modifyDate", ColumnType.DATE);
		columnTypeMap.put("template", ColumnType.STRING);

		dbQuery.executeStatement("SELECT * " +
				"FROM page AS p " +
				"INNER JOIN page_template AS pt " +
				"ON p.templateId = pt.id " +
				"WHERE p.id = " + pageId + " " +
				"AND p.active = 1 ", columnTypeMap);

		for(Map<String, Object> rowMap : dbQuery.getResultList()) {

			page.setId((Integer)rowMap.get("id"));
			page.setName((String)rowMap.get("name"));
			page.setActive((Boolean)rowMap.get("active"));
			page.setTemplateId((Integer)rowMap.get("templateId"));
			page.setCreationDate((Date)rowMap.get("creationDate"));
			page.setModifyDate((Date)rowMap.get("modifyDate"));
			page.setTemplate((String)rowMap.get("template"));
		}

		// get image data for page
		columnTypeMap.clear();
		columnTypeMap.put("id", ColumnType.INT);
		columnTypeMap.put("fileName", ColumnType.STRING);
		columnTypeMap.put("width", ColumnType.INT);
		columnTypeMap.put("height", ColumnType.INT);
		columnTypeMap.put("description", ColumnType.STRING);

		dbQuery.executeStatement("SELECT * " +
				"FROM page_content AS pc " +
				"INNER JOIN image AS i " +
				"ON pc.refid = i.id " +
				"WHERE pc.pageId = " + page.getId() + " " +
				"AND pc.type = 1 ", columnTypeMap);

		List<ContentImage> images = new ArrayList<ContentImage>();

		for(Map<String, Object> rowMap : dbQuery.getResultList()) {

			ContentImage image = new ContentImage();
			image.setId((Integer)rowMap.get("id"));
			image.setFileName((String)rowMap.get("fileName"));
			image.setWidth((Integer)rowMap.get("width"));
			image.setHeight((Integer)rowMap.get("height"));
			image.setDescription((String)rowMap.get("description"));

			images.add(image);
		}

		page.setImages(images);

		// get text data for page
		columnTypeMap.clear();
		columnTypeMap.put("id", ColumnType.INT);
		columnTypeMap.put("content", ColumnType.STRING);

		dbQuery.executeStatement("SELECT * " +
				"FROM page_content AS pc " +
				"INNER JOIN text AS t " +
				"ON pc.refid = t.id " +
				"WHERE pc.pageId = " + page.getId() + " " +
				"AND pc.type = 0", columnTypeMap);

		List<ContentText> texts = new ArrayList<ContentText>();

		for(Map<String, Object> rowMap : dbQuery.getResultList()) {

			ContentText text = new ContentText();
			text.setId((Integer)rowMap.get("id"));
			text.setContent((String)rowMap.get("content"));

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
