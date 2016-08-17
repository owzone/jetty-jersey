package org.example.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.example.websocket.EventSocket;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import java.net.InetSocketAddress;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

public class JettyServer {

  private final String hostname;
  private final int port;

  public JettyServer(final String hostname, final int port) {
    this.hostname = hostname;
    this.port = port;
  }

  public void start() {
    Server server = new Server(new InetSocketAddress(hostname, port));

    ResourceConfig resourceConfig = new ResourceConfig();
    resourceConfig.packages("org.example.rest.resources");

    ServletContainer servletContainer = new ServletContainer(resourceConfig);
    ServletHolder servletHolder = new ServletHolder(servletContainer);

    ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
    context.setContextPath("/");
    context.addServlet(servletHolder, "/*");

    server.setHandler(context);
    ServerContainer wscontainer;
    try {
      wscontainer = WebSocketServerContainerInitializer.configureContext(context);
      wscontainer.addEndpoint(EventSocket.class);

    } catch (ServletException | DeploymentException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    try {
      server.start();
      server.join();
    } catch (Exception e) {
      // TODO
      e.printStackTrace();
    }

  }

}
