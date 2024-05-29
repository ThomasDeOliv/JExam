package com.thomasdeoliv.itemsmanager.helpers;

import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorDialog {
    public static void handleException(Exception e) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);

        Throwable current = e;
        while (current != null) {
            current.printStackTrace(printWriter);
            current = current.getCause();
            if (current != null) {
                printWriter.println("Caused by:");
            }
        }

        String stackTrace = stringWriter.toString();
        TextArea textArea = new TextArea(stackTrace);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane(textArea);
        scrollPane.setFitToWidth(true);

        String className = e.getClass().getName();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("An exception of type " + (className + " was thrown"));
        alert.getDialogPane().setContent(scrollPane);
        alert.showAndWait();
    }
}
