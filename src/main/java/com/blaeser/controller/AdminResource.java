package com.blaeser.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("admin")
public class AdminResource {

	@GET
	public Response adminPage() {

		return Response.ok("This is the admin page !").build();
	}

	@Path("{pageName}")
	@GET
	public Response getPage(@PathParam("pageName") String pageName) {

		return Response.ok("This is admin page " + pageName + " !").build();
	}
}
