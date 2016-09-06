package org.example.main;

import org.example.server.JettyServer;
import org.example.server.JettyServerConfig;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

public class Main {

  @Parameter(names = { "--port", "-p" }, description = "The port on which to run Jetty")
  private int port = 8080;
  @Parameter(names = { "--host"}, description = "The host on which to run Jetty")
  private String host = "localhost";
  @Parameter(names = "-console-metrics", description = "Periodically dump Metrics data to the console")
  private boolean consoleMetrics = false;
  @Parameter(names = { "--help", "-h" }, help = true, description = "Displays this help message")
  private boolean help = false;

  public static void main(final String[] args) {
    Main main = new Main();
    JCommander jCommander = new JCommander(main, args);
    jCommander.setProgramName("java -jar jetty-jersey-example");
    if (main.help) {
      jCommander.usage();
      return;
    }
    main.start();
  }

  private void start() {
    JettyServer jettyServer = new JettyServer(getJettyConfig());
    jettyServer.start();
  }

  private JettyServerConfig getJettyConfig() {
    return new JettyServerConfig.Builder().hostname(host).port(port).consoleMetrics(consoleMetrics).build();
  }

}
