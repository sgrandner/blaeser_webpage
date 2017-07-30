package com.blaeser.controller;

import com.blaeser.models.*;
import com.blaeser.services.DbQuery;
import com.blaeser.services.MenuService;
import com.blaeser.services.PageService;
import org.glassfish.jersey.server.mvc.Viewable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("admin")
public class AdminResource {

	@GET
	@Produces("text/html")
	public Response adminPage() {

		Map<String, Object> modelMap = getAdminMenuData();

		return Response.ok(new Viewable("/admin", modelMap)).build();
	}

	@Path("p/{pageName}")
	@GET
	public Response showAdminPage(@PathParam("pageName") String pageName) {

		Map<String, Object> modelMap = getAdminMenuData();

		PageService pageService = new PageService();
		Page page = pageService.createPage(pageName);

		modelMap.put("page", page);

		return Response.ok(new Viewable("/admin", modelMap)).build();
	}

	@Path("pt/{pageTemplateName}")
	@GET
	public Response showAdminPageTemplate(@PathParam("pageTemplateName") String pageTemplateName) {

		Map<String, Object> modelMap = getAdminMenuData();

		PageService pageService = new PageService();
		PageTemplate pageTemplate = pageService.loadPageTemplate(pageTemplateName);

		modelMap.put("pageTemplate", pageTemplate);

		return Response.ok(new Viewable("/admin", modelMap)).build();
	}

	@Path("m/{menuName}")
	@GET
	public Response showAdminMenu(@PathParam("menuName") String menuName) {

		Map<String, Object> modelMap = getAdminMenuData();

		MenuService menuService = new MenuService();
		MenuItem menuItem = menuService.createMenuItem(menuName);

		modelMap.put("menuItem", menuItem);

		return Response.ok(new Viewable("/admin", modelMap)).build();
	}

	private Map<String, Object> getAdminMenuData() {

		// TODO shift to AdminService !!!
		DbQuery dbQuery = new DbQuery();

		// get all pages from db
		List<Page> pages = new ArrayList<>();

		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("active", ColumnType.BOOLEAN);
		dbQuery.setColumnType("templateId", ColumnType.INT);
		dbQuery.setColumnType("creationDate", ColumnType.DATE);
		dbQuery.setColumnType("modifyDate", ColumnType.DATE);

		dbQuery.query("selectPages");

		while(dbQuery.readResults()) {

			Page page = new Page();

			page.setId(dbQuery.getValueAsInteger("id"));
			page.setName(dbQuery.getValueAsString("name"));
			page.setActive(dbQuery.getValueAsBoolean("active"));
			page.setTemplateId(dbQuery.getValueAsInteger("templateId"));
			page.setCreationDate(dbQuery.getValueAsDate("creationDate"));
			page.setModifyDate(dbQuery.getValueAsDate("modifyDate"));

			pages.add(page);
		}

		// get all menus from db
		List<MenuItem> menuItems = new ArrayList<>();

		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("label", ColumnType.STRING);
		dbQuery.setColumnType("active", ColumnType.BOOLEAN);
		dbQuery.setColumnType("pageId", ColumnType.INT);
		dbQuery.setColumnType("creationDate", ColumnType.DATE);
		dbQuery.setColumnType("modifyDate", ColumnType.DATE);

		dbQuery.query("selectMenus");

		while(dbQuery.readResults()) {

			MenuItem menuItem = new MenuItem();

			menuItem.setName(dbQuery.getValueAsString("name"));
			menuItem.setLabel(dbQuery.getValueAsString("label"));
			menuItem.setActive(dbQuery.getValueAsBoolean("active"));
			menuItem.setPageId(dbQuery.getValueAsInteger("pageId"));

			menuItems.add(menuItem);
		}

		// get page templates from db
		List<PageTemplate> pageTemplates = new ArrayList<>();

		dbQuery.clear();
		dbQuery.setColumnType("id", ColumnType.INT);
		dbQuery.setColumnType("name", ColumnType.STRING);
		dbQuery.setColumnType("template", ColumnType.STRING);
		dbQuery.setColumnType("creationDate", ColumnType.DATE);
		dbQuery.setColumnType("modifyDate", ColumnType.DATE);

		dbQuery.query("selectPageTemplates");

		while(dbQuery.readResults()) {

			PageTemplate pageTemplate = new PageTemplate();

			pageTemplate.setId(dbQuery.getValueAsInteger("id"));
			pageTemplate.setName(dbQuery.getValueAsString("name"));
			pageTemplate.setTemplate(dbQuery.getValueAsString("template"));
			pageTemplate.setCreationDate(dbQuery.getValueAsDate("creationDate"));
			pageTemplate.setModifyDate(dbQuery.getValueAsDate("modifyDate"));

			pageTemplates.add(pageTemplate);
		}

		Map<String, Object> modelMap = new HashMap<String, Object>();
		modelMap.put("pages", pages);
		modelMap.put("menuItems", menuItems);
		modelMap.put("pageTemplates", pageTemplates);

		return modelMap;
	}
}
