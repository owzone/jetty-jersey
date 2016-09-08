package org.example.rest.resources;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import org.example.controller.UserController;

import com.codahale.metrics.annotation.Timed;

@Singleton
@Path("test")
public class TestResource {

	@Context
	UriInfo uriInfo;

	UserController userController = new UserController();

	public TestResource() {
		System.out.println("TestResource constructed");
	}

	@Timed
	@GET
	@Produces("text/plain")
	public String testResource() {
		return "Simple Text Response";
	}

}