package org.example.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("test")
public class TestResource {

	@GET
	@Produces("text/plain")
	public String getHello() {
		return "Simple Text Response";
	}
}