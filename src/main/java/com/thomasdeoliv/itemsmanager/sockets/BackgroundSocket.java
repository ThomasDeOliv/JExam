package com.thomasdeoliv.itemsmanager.sockets;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class BackgroundSocket extends Thread {
    private ServerSocket serverSocket;
    private Handler handler;
    private boolean running = false;

    public BackgroundSocket(Configuration config, Handler handler) {
        try {
            this.serverSocket = new ServerSocket(config.getServerPort());
            this.handler = handler;
        } catch (IOException e) {
            ErrorDialog.handleException(e);
        }
    }

    public void startServer() {
        running = true;
        this.start();
    }

    public void stopServer() throws IOException {
        running = false;
        serverSocket.close();
    }

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
