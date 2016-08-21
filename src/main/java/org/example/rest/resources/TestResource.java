package org.example.rest.resources;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import com.codahale.metrics.annotation.Timed;

@Path("test")
public class TestResource {

	@Timed
	@GET
	@Produces("text/plain")
	public String testResource() {
		return "Simple Text Response";
	}
}