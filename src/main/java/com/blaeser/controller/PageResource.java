package com.blaeser.controller;

import com.blaeser.models.ColumnType;
import com.blaeser.models.Menu;
import com.blaeser.models.Page;
import com.blaeser.services.DbQuery;
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

		StringBuffer sb = new StringBuffer();

		DbQuery dbQuery = new DbQuery();
		Map<String, ColumnType> columnTypeMap = new HashMap<>();

		// first data set
		columnTypeMap.clear();
		columnTypeMap.put("id", ColumnType.INT);
		columnTypeMap.put("name", ColumnType.STRING);
		columnTypeMap.put("active", ColumnType.BOOLEAN);
		columnTypeMap.put("creationDate", ColumnType.DATE);

		dbQuery.executeStatement("SELECT * FROM page", columnTypeMap);

		sb.append(dbQuery.toString());
		sb.append("<br/><br/>");

		// second data set
		columnTypeMap.clear();
		columnTypeMap.put("name", ColumnType.STRING);
		columnTypeMap.put("creationDate", ColumnType.DATE);

		dbQuery.executeStatement("SELECT name, creationDate FROM page WHERE creationDate > '2016-11-01 00:00:00'", columnTypeMap);

		sb.append(dbQuery.toString());

		return Response.ok(sb.toString()).build();
	}
}
