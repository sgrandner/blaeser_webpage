package com.blaeser.controller;

import com.blaeser.models.ColumnType;
import com.blaeser.models.Menu;
import com.blaeser.models.Page;
import com.blaeser.services.DbQuery;
import com.blaeser.services.MenuService;
import com.blaeser.services.PageService;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
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
	public Response getPageByMenuName(@PathParam("menuName") String menuName) {

		MenuService menuService = new MenuService();
		PageService pageService = new PageService();

		Menu menu = menuService.createMenu(menuName);
		Page page = pageService.createPage(menu.getSelectedPageId());

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("menu", menu);
		modelMap.put("page", page);

		// TODO case page == null !!!

		return Response.ok(new Viewable("/page", modelMap)).build();
	}

	@GET
	@Path("p/{pageName}")
	@Produces("text/html")
	public Response getPageByPageName(@PathParam("pageName") String pageName) {

		PageService pageService = new PageService();

		Page page = pageService.createPage(pageName);

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("page", page);

		// TODO case page == null !!!

		return Response.ok(new Viewable("/page", modelMap)).build();
	}

	// test data
	@GET
	@Path("/data")
	public Response dataPage() {

		StringBuffer sb = new StringBuffer();
		DbQuery dbQuery = new DbQuery();

		// first data set
		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("active", ColumnType.BOOLEAN);
		dbQuery.setColumnType("creationDate", ColumnType.DATE);

		dbQuery.query("selectPages");

		sb.append(dbQuery.toString());
		sb.append("<br/><br/>");

		// second data set
		dbQuery.clear();
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("creationDate", ColumnType.DATE);

		dbQuery.query("testSelectPagesLaterThanCreationDate", "2016-11-01 00:00:00");

		sb.append(dbQuery.toString());

		return Response.ok(sb.toString()).build();
	}
}
