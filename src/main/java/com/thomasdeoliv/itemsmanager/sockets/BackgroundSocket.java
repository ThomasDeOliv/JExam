package com.thomasdeoliv.itemsmanager.sockets;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Represents a background server socket that listens for incoming connections and handles messages received from clients.
 */
public class BackgroundSocket extends Thread {

	/**
	 * The server socket that listens for incoming connections.
	 */
	private ServerSocket serverSocket;

	/**
	 * The handler to process messages received from clients.
	 */
	private Handler handler;

	/**
	 * Boolean flag to control the running state of the server.
	 */
	private boolean running = false;

	/**
	 * Constructor initializes the server socket.
	 *
	 * @param config  the configuration object with the server port.
	 * @param handler the handler to process messages received from clients.
	 */
	public BackgroundSocket(Configuration config, Handler handler) {
		try {
			this.serverSocket = new ServerSocket(config.getServerPort());
			this.handler = handler;
		} catch (IOException e) {
			ErrorDialog.handleException(e);
		}
	}

	/**
	 * Starts the server by setting the running flag to true and starting the thread.
	 */
	public void startServer() {
		running = true;
		this.start();
	}

	/**
	 * Stops the server by setting the running flag to false and closing the server socket.
	 *
	 * @throws IOException if an I/O error occurs when closing the server socket.
	 */
	public void stopServer() throws IOException {
		running = false;
		serverSocket.close();
	}

	/**
	 * The run method contains the main loop that listens for incoming client connections and handles messages received from clients.
	 */
	@Override
	public void run() {
		while (running) {
			try {
				// Accept connection
				Socket clientSocket = serverSocket.accept();
				// Handle messages received
				try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					 PrintWriter printWriter = new PrintWriter(clientSocket.getOutputStream(), true)) {
					// Check input
					String inputLine;
					while ((inputLine = bufferedReader.readLine()) != null) {
						// Handle message in a view model
						this.handler.handleMessage(inputLine);
						// Pass input message to output
						printWriter.println(inputLine);
					}
				}
			} catch (IOException e) {
				// Break loop if exist
				if (!running) {
					break;
				}
				// Show error
				ErrorDialog.handleException(e);
			}
		}
	}
}
