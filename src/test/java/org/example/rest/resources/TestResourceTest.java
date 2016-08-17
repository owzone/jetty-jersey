package org.example.rest.resources;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class TestResourceTest extends JerseyTest {

  @Override
  protected Application configure() {
    return new ResourceConfig(TestResource.class);
  }

  @Test
  public void testGetHello() {
    final String testResourceValue = target("test").request().get(String.class);
    assertThat(testResourceValue, is("Simple Text Response"));
  }
}
