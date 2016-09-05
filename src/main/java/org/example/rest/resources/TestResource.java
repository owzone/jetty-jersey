package org.example.rest.resources;

import com.codahale.metrics.annotation.Timed;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Singleton
@Path("test")
public class TestResource {

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