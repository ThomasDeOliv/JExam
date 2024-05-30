package com.thomasdeoliv.itemsmanager.sockets;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Represents a client socket that connects to a server.
 */
public class ClientSocket extends Thread {

	/**
	 * The client socket used to connect to the server.
	 */
	private Socket clientSocket;

	/**
	 * Constructor initializes the client socket with the specified configuration.
	 *
	 * @param config the configuration object with the server host and port.
	 */
	public ClientSocket(Configuration config) {
		try {
			this.clientSocket = new Socket(config.getServerHost(), config.getServerPort());
		} catch (IOException e) {
			ErrorDialog.handleException(e);
		}
	}

	/**
	 * The run method contains the main loop that handles communication with the server.
	 */
	@Override
	public void run() {
		try {
			try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
				 PrintWriter printWriter = new PrintWriter(this.clientSocket.getOutputStream(), true)) {
				// Read client message
				String inputLine;
				while ((inputLine = bufferedReader.readLine()) != null) {
					// Pass input message to output
					printWriter.println(inputLine);
				}
			}
		} catch (IOException e) {
			ErrorDialog.handleException(e);
		}
	}
}