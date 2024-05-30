package com.thomasdeoliv.itemsmanager.ui.viewsmodels.base;

import com.thomasdeoliv.itemsmanager.Launcher;
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

/**
 * Represents the ViewModel for a basic chat layout.
 */
public class BaseChatLayoutViewModel implements Handler {

	/**
	 * ObservableList to hold chat messages.
	 */
	private final ObservableList<String> messages;

	/**
	 * Server port number.
	 */
	private final int serverPort;

	/**
	 * Background socket task for server communication.
	 */
	private final BackgroundSocket socketBackgroundTask;

	/**
	 * Constructor initializes the ViewModel, and starts the background socket task.
	 */
	public BaseChatLayoutViewModel() {
		serverPort = Launcher.getConfiguration().getServerPort();
		socketBackgroundTask = new BackgroundSocket(Launcher.getConfiguration(), this);
		messages = FXCollections.observableArrayList();
	}

	/**
	 * Text node to display the server port.
	 */
	@FXML
	public Text port;

	/**
	 * TextField for entering the recipient of the message.
	 */
	@FXML
	public TextField recipient;

	/**
	 * ListView to display the chat messages.
	 */
	@FXML
	public ListView<String> list;

	/**
	 * TextArea for composing messages to be sent.
	 */
	@FXML
	public TextArea message;

	/**
	 * Button to send messages.
	 */
	@FXML
	public Button send;

	/**
	 * Initializes the ViewModel.
	 */
	@FXML
	public void initialize() {
		// Set port field
		this.port.setText("Votre port : " + serverPort);
		// Start socket server background task
		this.socketBackgroundTask.start();
		// Add all messages in the listview
		this.list.getItems().addAll(messages);
	}

	/**
	 * Handles incoming messages by adding them to the messages list.
	 *
	 * @param message the message received from the server.
	 */
	@Override
	public void handleMessage(String message) {
		messages.add(message);
	}
}
