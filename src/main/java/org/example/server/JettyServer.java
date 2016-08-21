package org.example.server;

import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.example.server.metrics.ExampleHealthCheckServletContextListener;
import org.example.server.metrics.ExampleMetricsServletContextListener;
import org.example.server.metrics.MetricsUtil;
import org.example.websocket.EventSocket;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.jersey2.InstrumentedResourceMethodApplicationListener;
import com.codahale.metrics.servlets.AdminServlet;
import com.codahale.metrics.servlets.HealthCheckServlet;
import com.codahale.metrics.servlets.MetricsServlet;
import com.codahale.metrics.servlets.PingServlet;
import com.codahale.metrics.servlets.ThreadDumpServlet;

public class JettyServer {

	Logger logger = LoggerFactory.getLogger(JettyServer.class);
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
		registerMetrics(resourceConfig);
		ServletContainer servletContainer = new ServletContainer(resourceConfig);
		ServletHolder servletHolder = new ServletHolder(servletContainer);

		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath("/");
		context.addServlet(servletHolder, "/api/*");

		registerMetricsServlets(context);
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

	private void registerMetricsServlets(ServletContextHandler context) {
		context.addEventListener(new ExampleHealthCheckServletContextListener());
		context.addEventListener(new ExampleMetricsServletContextListener());
		context.addServlet(AdminServlet.class, "/admin");
		context.addServlet(HealthCheckServlet.class, "/admin/healthcheck");
		context.addServlet(MetricsServlet.class, "/admin/metrics");
		context.addServlet(PingServlet.class, "/admin/ping");
		context.addServlet(ThreadDumpServlet.class, "/admin/threads");
	}

	private void registerMetrics(final ResourceConfig resourceConfig) {
		logger.info("Registering Metrics service");
		MetricRegistry metricsReg = MetricsUtil.getMetricsRegistry();
		resourceConfig.register(new InstrumentedResourceMethodApplicationListener(metricsReg));
		ConsoleReporter.forRegistry(metricsReg).convertRatesTo(TimeUnit.SECONDS)
				.convertDurationsTo(TimeUnit.MILLISECONDS).build().start(10, TimeUnit.SECONDS);
		logger.info("Registered Metrics service");
	}

}
