package com.thomasdeoliv.itemsmanager;

import com.thomasdeoliv.itemsmanager.config.Configuration;
import com.thomasdeoliv.itemsmanager.database.daos.implementations.ProjectDAO;
import com.thomasdeoliv.itemsmanager.database.daos.implementations.TaskDAO;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Project;
import com.thomasdeoliv.itemsmanager.database.entities.implementations.Task;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URL;

/**
 * The class which contains the Main function.
 */
public class Launcher extends Application {

	private static Stage primaryStage;

	public static final Configuration configuration = new Configuration();
	public static final ProjectDAO projectDAO = new ProjectDAO(Launcher.configuration);
	public static final TaskDAO taskDAO = new TaskDAO(Launcher.configuration);

	public static Project SelectedProject = null;
	public static Task SelectedTask = null;
	public static SimpleBooleanProperty DisplayTasks = new SimpleBooleanProperty(false);

	@Override
	public void start(@Nullable Stage primaryStage) {

		try {
			// Ensure the stage exists
			if (primaryStage == null) {
				throw new RuntimeException();
			}
			// Define the main stage
			Launcher.primaryStage = primaryStage;
			// Define title
			primaryStage.setTitle("Items manager");
			// Search for layout fxml view
			URL layoutURL = this.getClass().getResource("/views/layouts/layout.fxml");
			// Ensure layout exists
			if (layoutURL == null) {
				throw new FileNotFoundException("Cannot find layout file.");
			}
			// Load layout
			Parent mainView = FXMLLoader.load(layoutURL);
			// Define scene
			primaryStage.setScene(new Scene(mainView));
			// Show the main stage
			primaryStage.show();
		} catch (IOException e) {

			StringWriter stringWriter = new StringWriter();
			PrintWriter printWriter = new PrintWriter(stringWriter);

			// Print all causes of the exception
			Throwable current = e;
			while (current != null) {
				current.printStackTrace(printWriter);
				current = current.getCause();
				if (current != null) {
					printWriter.println("Caused by:");
				}
			}

			String stackTrace = stringWriter.toString();

			// Create TextArea for stacktrace
			TextArea textArea = new TextArea(stackTrace);
			textArea.setEditable(false);
			textArea.setWrapText(true);

			// Add TextArea to ScrollPane
			ScrollPane scrollPane = new ScrollPane(textArea);
			scrollPane.setFitToWidth(true);

			// Create and configure Alert
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error");
			String headerText = "An exception of type " + e.getClass().getName() + " was thrown";
			alert.setHeaderText(headerText);
			alert.getDialogPane().setContent(scrollPane);

			// Show alert
			alert.showAndWait();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
