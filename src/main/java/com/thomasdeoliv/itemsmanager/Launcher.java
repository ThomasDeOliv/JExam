package com.thomasdeoliv.itemsmanager;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.implementations.ProjectDAO;
import com.thomasdeoliv.itemsmanager.database.daos.implementations.TaskDAO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import com.thomasdeoliv.itemsmanager.helpers.ErrorDialog;
import com.thomasdeoliv.itemsmanager.helpers.FXHelpers;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The class which contains the Main function.
 */
public class Launcher extends Application {

	private static final ObjectProperty<Project> selectedProject = new SimpleObjectProperty<>(null);
	private static final ObjectProperty<Task> selectedTask = new SimpleObjectProperty<>(null);
	private static final SimpleBooleanProperty displayTasks = new SimpleBooleanProperty(false);

	private static final Configuration configuration;
	private static final ProjectDAO projectDAO;
	private static final TaskDAO taskDAO;

	// Static bloc to turn projectDAO and taskDAO as final
	static {
		Configuration tempConfiguration = null;
		ProjectDAO tempProjectDAO = null;
		TaskDAO tempTaskDAO = null;
		try {
			tempConfiguration = new Configuration();
			tempProjectDAO = new ProjectDAO(tempConfiguration);
			tempTaskDAO = new TaskDAO(tempConfiguration);
		} catch (IOException e) {
			ErrorDialog.handleException(e);
		}
		configuration = tempConfiguration;
		projectDAO = tempProjectDAO;
		taskDAO = tempTaskDAO;
	}

	public static void main(String[] args) {
		launch(args);
	}

	public static Configuration getConfiguration() {
		return configuration;
	}

	public static ProjectDAO getProjectDAO() {
		return projectDAO;
	}

	public static TaskDAO getTaskDAO() {
		return taskDAO;
	}

	public static ObjectProperty<Project> selectedProjectProperty() {
		return selectedProject;
	}

	public static ObjectProperty<Task> selectedTaskProperty() {
		return selectedTask;
	}

	public static SimpleBooleanProperty displayTasksProperty() {
		return displayTasks;
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			// Displayed Projects
			if (primaryStage == null) {
				// Ensure provided stage is not null
				throw new RuntimeException("Primary stage is null.");
			}
			primaryStage.setTitle("Items Manager"); // Title
			FXHelpers.setApplicationIcon(primaryStage, "/images/icon.png"); // Icon
			Parent mainView = FXHelpers.loadFXML("/views/layouts/MainLayout.fxml"); // FXML rendering
			primaryStage.setScene(new Scene(mainView)); // Scene
			// OnClose Window event
			primaryStage.setOnCloseRequest(windowEvent -> {
				// Kill app
				Platform.exit();
				// Set normal status
				System.exit(0);
			});
			primaryStage.show();

			// Chat
			Stage chatStage = new Stage(); // Define another stage
			chatStage.setTitle("Chat"); // Title
			chatStage.setResizable(false); // Not resizable
			FXHelpers.setApplicationIcon(chatStage, "/images/icon.png"); // Icon
			Parent chatView = FXHelpers.loadFXML("/views/layouts/ChatLayout.fxml"); // FXML rendering
			chatStage.setScene(new Scene(chatView)); // Scene
			// OnClose Window event
			chatStage.setOnCloseRequest(t -> {
				// Kill app
				Platform.exit();
				// Set normal status
				System.exit(0);
			});
			chatStage.show();
		} catch (IOException e) {
			// Handle error case
			ErrorDialog.handleException(e);
		}
	}
}
