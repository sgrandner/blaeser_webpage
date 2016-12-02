package com.blaeser.controller;

import com.blaeser.models.Page;
import com.blaeser.services.PageService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("page")
public class PageResource {

	@Inject
	private PageService pageService;

	@GET
	public Response mainPage() {

		return Response.ok("This is the mainpage.").build();
	}

	@Path("{pageName}")
	@GET
	public Response specialPage(@PathParam("pageName") String pageName) {

		return Response.ok("This is page " + pageName + " !").build();
	}

	@Path("getpages")
	@GET
	@Produces("application/json")
	public Response getPages() {

		String pageString = "";

		for(Page page : pageService.getAll()) {
			pageString += "id: " + page.getId() + ", name: " + page.getName();
		}

		return Response.ok(pageString).build();
	}
}
