package com.blaeser.controller;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/page")
public class PageResource {

	@GET
	public String mainPage() {

		return "This is the main page.";
	}

	@Path("{first}-{last}")
	@GET
	public String specialPage(@PathParam("first") String first, @PathParam("last") String last) {

		return first + "..." + last;
	}
}
