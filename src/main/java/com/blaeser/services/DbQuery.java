package com.blaeser.services;

import com.blaeser.models.ColumnType;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbQuery {

	private List<Map<String, Object>> resultList = new ArrayList<>();

	public void executeStatement(String statementString, Map<String, ColumnType> columnTypeMap) {

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

	// TODO instead of simple getter method implement type specific getter methods with an internal iterator
	// in calling class:   while(get result row) get key as type

	public List<Map<String, Object>> getResultList() {
		return resultList;
	}
}
