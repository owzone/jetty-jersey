package org.example.main;

import org.example.server.JettyServer;

public class Main {

	public static void main(final String[] args) {
		Main main = new Main();
		main.start();
	}

	private void start() {
		JettyServer jettyServer = new JettyServer("localhost", 8080);
		jettyServer.start();
	}

}
