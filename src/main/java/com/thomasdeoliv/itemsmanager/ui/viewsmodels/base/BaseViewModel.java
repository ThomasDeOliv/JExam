package com.thomasdeoliv.itemsmanager.ui.viewsmodels.base;

import com.thomasdeoliv.itemsmanager.Launcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class BaseViewModel {

	@FXML
	public VBox root;

	@FXML
	public void initialize() {
		// Add a listener on each changes of the Simple boolean value
		Launcher.DisplayTasks.addListener((observable, oldValue, newValue) -> {
			loadConditionalFXML(newValue);
		});

		// First load
		loadConditionalFXML(Launcher.DisplayTasks.get());
	}

	private void loadConditionalFXML(boolean condition) {
		// Clear all elements
		root.getChildren().clear();
		// Conditional
		String fxmlFile = condition ? "/views/containers/taskscontainer.fxml" : "/views/containers/projectcontainer.fxml";
		try {
			URL url = getClass().getResource(fxmlFile);
			if (url != null) {
				Node content = FXMLLoader.load(url);
				root.getChildren().add(content);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
