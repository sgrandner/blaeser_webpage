package com.blaeser.services;

import com.blaeser.models.SqlTemplateGroupType;
import com.blaeser.models.SqlTemplateType;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SqlTemplateManager {

	private static SqlTemplateManager instance = new SqlTemplateManager();

	private Map<String, String> sqlTemplateMap;

	public static SqlTemplateManager getInstance() {
		return instance;
	}

	private SqlTemplateManager() {

		readSqlTemplates();
	}

	private void readSqlTemplates() {

		try {

			URL url = PageService.class.getResource("/sql/sql_queries.xml");
			File file = new File(url.getFile());

			// Java Architecture for XML Binding (JAXB)
			JAXBContext jaxbContext = JAXBContext.newInstance("com.blaeser.models");
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();

			JAXBElement<SqlTemplateGroupType> sqlTemplateGroup = (JAXBElement<SqlTemplateGroupType>)unmarshaller.unmarshal(file);

			sqlTemplateMap = new HashMap<>();

			for(SqlTemplateType sqlTemplate : sqlTemplateGroup.getValue().getSqlTemplate()) {

				sqlTemplateMap.put(sqlTemplate.getName(), sqlTemplate.getValue());
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public Map<String, String> getSqlTemplateMap() {
		return sqlTemplateMap;
	}
}
