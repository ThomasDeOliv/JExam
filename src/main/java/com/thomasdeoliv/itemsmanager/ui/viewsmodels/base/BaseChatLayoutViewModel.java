package com.thomasdeoliv.itemsmanager.ui.viewsmodels.base;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;
import com.thomasdeoliv.itemsmanager.sockets.BackgroundSocket;
import com.thomasdeoliv.itemsmanager.sockets.Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.IOException;

public class BaseChatLayoutViewModel implements Handler {

    @FXML
    public Text port;
    @FXML
    public TextField recipient;
    @FXML
    public ListView<String> list;
    @FXML
    public TextArea message;
    @FXML
    public Button send;
    private ObservableList<String> messages;
    private int serverPort;
    private BackgroundSocket socketBackgroundTask;

    public BaseChatLayoutViewModel() {
        try {
            serverPort = Launcher.getConfiguration().getServerPort();
            socketBackgroundTask = new BackgroundSocket(Launcher.getConfiguration(), this);
            messages = FXCollections.observableArrayList();
        } catch (IOException e) {
            ErrorDialog.handleException(e);
        }
    }

    @FXML
    public void initialize() {
        this.port.setText("Votre port : " + serverPort);
        this.socketBackgroundTask.start();
        this.list.getItems().addAll(messages);
    }

    @Override
    public void handleMessage(String message) {
        messages.add(message);
    }
}
