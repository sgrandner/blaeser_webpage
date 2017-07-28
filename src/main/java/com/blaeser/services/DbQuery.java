package com.blaeser.services;

import com.blaeser.models.ColumnType;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DbQuery {

	private List<Map<String, Object>> resultList = new ArrayList<>();
	private Map<String, ColumnType> columnTypeMap = new HashMap<>();
	private Integer rowIterator = null;

	public void setColumnType(String columnName, ColumnType columnType) {

		columnTypeMap.put(columnName, columnType);
	}

	public void clear() {

		resultList.clear();
		columnTypeMap.clear();
		rowIterator = null;
	}

	public void query(String templateName, Object... params) {

		StringBuffer processedSqlTemplate = new StringBuffer();

		String template = SqlTemplateManager.getInstance().getSqlTemplateMap().get(templateName);
		Matcher matcher = Pattern.compile("\\?").matcher(template);

		int index = 0;
		List<Object> paramList = new ArrayList<>();
		Collections.addAll(paramList, params);

		try {

			while(matcher.find()) {
				Object param = paramList.get(index++);
				matcher.appendReplacement(processedSqlTemplate, param.toString());
			}

			matcher.appendTail(processedSqlTemplate);
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		if(index != paramList.size()) {
			// TODO error logging
		}

		executeStatement(processedSqlTemplate.toString(), columnTypeMap);
	}

	private void executeStatement(String statementString, Map<String, ColumnType> columnTypeMap) {

		resultList.clear();

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;

		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");

			// This works too
			// Context envCtx = (Context) ctx.lookup("java:comp/env");
			// DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

			conn = ds.getConnection();
			st = conn.createStatement();

			rs = st.executeQuery(statementString);

			while (rs.next()) {

				Map<String, Object> rowMap = new HashMap<String, Object>();

				for(String key : columnTypeMap.keySet()) {

					Object value = null;

					switch (columnTypeMap.get(key)) {
						case STRING:
							value = rs.getString(key);
							break;
						case INT:
							value = rs.getInt(key);
							break;
						case LONG:
							value = rs.getLong(key);
							break;
						case FLOAT:
							value = rs.getFloat(key);
							break;
						case DOUBLE:
							value = rs.getDouble(key);
							break;
						case BOOLEAN:
							value = rs.getBoolean(key);
							break;
						case DATE:
							value = rs.getDate(key);
							break;
					}

					rowMap.put(key, value);
				}

				resultList.add(rowMap);
			}
		}
		catch (Exception ex) {
			ex.printStackTrace();
		}
		finally {
			try { if (rs != null) rs.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (st != null) st.close(); } catch (SQLException e) { e.printStackTrace(); }
			try { if (conn != null) conn.close(); } catch (SQLException e) { e.printStackTrace(); }
		}
	}

	public String toString() {

		StringBuffer sb = new StringBuffer();

		for(Map<String, Object> rowMap : resultList) {

			for(String key : rowMap.keySet()) {
				sb.append(key + ": " + rowMap.get(key) + ", ");
			}

			sb.append("<br/>");
		}

		return sb.toString();
	}

	public boolean readResults() {

		if(rowIterator == null) {
			rowIterator = 0;
		}
		else {
			rowIterator++;
		}

		return rowIterator < resultList.size();
	}

	private Object getValue(String columnName) {

		return resultList.get(rowIterator).get(columnName);
	}

	public String getValueAsString(String columnName) {

		return getValue(columnName).toString();
	}

	public Integer getValueAsInteger(String columnName) {

		return Integer.parseInt(getValue(columnName).toString());
	}

	// TODO

	public Boolean getValueAsBoolean(String columnName) {

		return getValue(columnName).toString().equals("1");
	}

	public Date getValueAsDate(String columnName) {

		return (Date)getValue(columnName);
	}
}
