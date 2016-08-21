# jetty-jersey

A simple Jetty + Jersey project to act as a basis for full application builds.

This project is intended to run on Java 1.8

Running the Main class will initiate an embedded Jetty server running on port 8080

The Jetty server handles REST requests (see TestResource) and WebSocket requests (see EventSocket)

Logging is implemented using SLF4J and Log4J2 - the underlying implementation can be changed by modifying the binding in the pom.xml file
Logging configuration is available in log4j2.xml

Metrics are tracked using dropwizard.Metrics - see admin Servlet for in life server Metrics

Test URLs:
* http://localhost:8080/api/test
* http://localhost:8080/ws
* http://localhost:8080/admin
