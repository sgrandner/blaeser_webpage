package com.blaeser.controller;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

//@ApplicationPath("resources")		// not needed since class is defined in web.xml
public class JAXRSConfiguration extends ResourceConfig {

	public JAXRSConfiguration() {

		register(JspMvcFeature.class);

		// NOTE properties replace web.xml entries
//		property(JspMvcFeature.TEMPLATE_BASE_PATH, "/WEB-INF/jsp");		// use this if not defined in web.xml by init-param jersey.config.server.mvc.templateBasePath.jsp

		register(PageResource.class);
		register(AdminResource.class);
	}

	// possible if extends Application instead of ResourceConfig
	//	@Override
	//	public Set<Class<?>> getClasses() {
	//		return new HashSet<Class<?>>(Arrays.asList(PageResource.class));
	//	}
}
