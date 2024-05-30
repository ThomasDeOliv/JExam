package com.thomasdeoliv.itemsmanager.sockets;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket extends Thread {
    private Socket clientSocket;

    public ClientSocket(Configuration config) {
        try {
            this.clientSocket = new Socket(config.getServerHost(), config.getServerPort());
        } catch (IOException e) {
            ErrorDialog.handleException(e);
        }
    }

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