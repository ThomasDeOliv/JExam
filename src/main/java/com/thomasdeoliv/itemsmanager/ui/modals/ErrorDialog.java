package com.thomasdeoliv.itemsmanager.ui.modals;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * A dialog to display error messages with an icon and a message.
 */
public class ErrorDialog extends Stage {

	/**
	 * Error Dialog constructor.
	 *
	 * @param title        Title of the dialog.
	 * @param errorMessage Message details.
	 */
	public ErrorDialog(String title, String errorMessage) {
		// Set stage properties
		this.setTitle(title);
		this.initModality(Modality.APPLICATION_MODAL);
		this.initStyle(StageStyle.UTILITY);
		this.setResizable(false);

		// Create components
		Label messageLabel = new Label(errorMessage);
		messageLabel.setWrapText(true);

		// Load the icon
		Image icon = new Image("file:src/main/resources/images/error.png");
		ImageView iconView = new ImageView(icon);
		iconView.setFitHeight(50);
		iconView.setFitWidth(50);
		VBox.setMargin(iconView, new Insets(0, 20, 0, 0));

		// Create a button to close the window
		Button okButton = new Button("OK");
		okButton.setOnAction(e -> this.close());

		// Create a panel for the icon and message
		HBox messagePanel = new HBox();
		messagePanel.setAlignment(Pos.CENTER_LEFT);
		messagePanel.getChildren().addAll(iconView, messageLabel);

		// Create a panel for the button
		HBox buttonPanel = new HBox();
		buttonPanel.setAlignment(Pos.CENTER);
		buttonPanel.getChildren().add(okButton);
		buttonPanel.setPadding(new Insets(10, 0, 0, 0));

		// Set layout and add components
		BorderPane root = new BorderPane();
		root.setCenter(messagePanel);
		root.setBottom(buttonPanel);
		BorderPane.setMargin(messagePanel, new Insets(10));

		Scene scene = new Scene(root, 450, 150);
		this.setScene(scene);
		this.centerOnScreen();
	}
}
