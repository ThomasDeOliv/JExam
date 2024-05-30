package com.thomasdeoliv.itemsmanager.ui.viewsmodels.base;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

/**
 * Represents the ViewModel for the main layout of the application.
 */
public class BaseMainLayoutViewModel {

	/**
	 * Loads the appropriate FXML file based on the given condition.
	 *
	 * @param condition a boolean value determining which FXML file to load.
	 */
	private void loadConditionalFXML(boolean condition) {
		// Clear all elements from the root container
		root.getChildren().clear();
		// Determine which FXML file to load based on the condition
		String fxmlFile = condition ? "/views/containers/taskscontainer.fxml" : "/views/containers/projectcontainer.fxml";
		try {
			// Load the FXML file and add its content to the root container
			URL url = getClass().getResource(fxmlFile);
			if (url != null) {
				Node content = FXMLLoader.load(url);
				root.getChildren().add(content);
			}
		} catch (IOException e) {
			// Handle any IOException that occurs during the loading process
			ErrorDialog.handleException(e);
		}
	}

	/**
	 * The root container for the layout.
	 */
	@FXML
	public VBox root;

	/**
	 * Initializes the ViewModel.
	 */
	@FXML
	public void initialize() {
		// Add a listener on each changes of the Simple boolean value
		Launcher.selectedTaskProperty().addListener((observable, oldValue, newValue) -> {
			loadConditionalFXML(Launcher.displayTasksProperty().get());
		});
		// First load
		loadConditionalFXML(Launcher.displayTasksProperty().get());
	}
}
