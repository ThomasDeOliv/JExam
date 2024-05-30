package com.thomasdeoliv.itemsmanager.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Provides a utility method for displaying detailed error messages in an alert dialog.
 */
public class ErrorDialog {

	/**
	 * Handles the given exception by displaying an error dialog with the exception's stack trace.
	 *
	 * @param e the exception to handle.
	 */
	public static void handleException(Exception e) {
		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);

		// Get the complete stack trace including causes
		Throwable current = e;
		while (current != null) {
			current.printStackTrace(printWriter);
			current = current.getCause();
			if (current != null) {
				printWriter.println("Caused by:");
			}
		}

		// Convert stack trace to string
		String stackTrace = stringWriter.toString();
		TextArea textArea = new TextArea(stackTrace);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		// Create a scrollable pane for the stack trace
		ScrollPane scrollPane = new ScrollPane(textArea);
		scrollPane.setFitToWidth(true);

		// Get the exception's class name
		String className = e.getClass().getName();

		// Create and display the alert dialog
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("An exception of type " + className + " was thrown");
		alert.getDialogPane().setContent(scrollPane);
		alert.showAndWait();
	}
}
