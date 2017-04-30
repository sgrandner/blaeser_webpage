package com.blaeser.controller;

import com.blaeser.models.Menu;
import com.blaeser.models.Page;
import com.blaeser.services.MenuService;
import com.blaeser.services.PageService;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Path("page")
public class PageResource {

	@GET
	@Produces("text/html")
	public Response mainPage() {

		return Response.ok("This is the mainpage.").build();
	}

	@GET
	@Path("{menuName}")
	@Produces("text/html")
	public Viewable getPageByMenuName(@PathParam("menuName") String menuName) {

		MenuService menuService = new MenuService();
		PageService pageService = new PageService();

		Menu menu = menuService.createMenu(menuName);
		Page page = pageService.createPage(menu.getPageId());

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("menu", menu);
		modelMap.put("page", page);

		// TODO case page == null !!!

		return new Viewable("/page", modelMap);
	}

	@GET
	@Path("p/{pageName}")
	@Produces("text/html")
	public Viewable getPageByPageName(@PathParam("pageName") String pageName) {

		PageService pageService = new PageService();

		Page page = pageService.createPage(pageName);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("page", page);

		// TODO case page == null !!!

		return new Viewable("/page", modelMap);
	}

	@GET
	@Path("/data")
	public Response dataPage() {

		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		StringBuffer sb = new StringBuffer();

		try {
			InitialContext ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/TestDB");

			// This works too
			// Context envCtx = (Context) ctx.lookup("java:comp/env");
			// DataSource ds = (DataSource) envCtx.lookup("jdbc/TestDB");

			conn = ds.getConnection();
			st = conn.createStatement();

			rs = st.executeQuery("SELECT * FROM page");

			sb.append("Hier gibts einige Daten:<br><br>");

			while (rs.next()) {

				String id = rs.getString("id");
				String name = rs.getString("name");
				String active = rs.getString("active");
				String creationDate = rs.getString("creationDate");

				sb.append("id: " + id + ", name: " + name + ", active: " + active + ", creationDate: " + creationDate + "<br/>");
			}

			sb.append("<br/><br/>");

			rs = st.executeQuery("SELECT name, creationDate FROM page WHERE creationDate > '2016-11-01 00:00:00'");

			while(rs.next()) {

				String name = rs.getString("name");
				String creationDate = rs.getString("creationDate");

				sb.append("name: " + name + ", creationDate: " + creationDate + "<br/>");

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

		return Response.ok(sb.toString()).build();
	}
}
