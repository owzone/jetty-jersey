# jetty-jersey

A simple Jetty + Jersey project to act as a basis for full application builds.

This project is inteded to run on Java 1.8

Running the Main class will initiate an embedded Jetty server running on port 8080

The Jetty server handles REST requests (see TestResource) and WebSocket requests (see EventSocket)

Test URLs:
http://localhost:8080/test
http://localhost:8080/ws
