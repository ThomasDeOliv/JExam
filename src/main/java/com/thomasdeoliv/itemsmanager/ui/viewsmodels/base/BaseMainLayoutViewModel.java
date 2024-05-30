package com.thomasdeoliv.itemsmanager.ui.viewsmodels.base;

import com.thomasdeoliv.itemsmanager.Launcher;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;

public class BaseMainLayoutViewModel {

    @FXML
    public VBox root;

    @FXML
    public void initialize() {
        // Add a listener on each changes of the Simple boolean value
        Launcher.selectedTaskProperty().addListener((observable, oldValue, newValue) -> {
            loadConditionalFXML(Launcher.displayTasksProperty().get());
        });

        // First load
        loadConditionalFXML(Launcher.displayTasksProperty().get());
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
            ErrorDialog.handleException(e);
        }
    }
}
