package com.thomasdeoliv.itemsmanager.helpers;

import com.thomasdeoliv.itemsmanager.Launcher;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Provides utility methods for working with JavaFX.
 */
public class FXHelpers {

	/**
	 * Sets the application icon for the specified stage.
	 *
	 * @param stage    the stage to set the icon for.
	 * @param iconPath the path to the icon resource.
	 */
	public static void setApplicationIcon(Stage stage, String iconPath) {
		InputStream iconInputStream = Launcher.class.getResourceAsStream(iconPath);
		if (iconInputStream != null) {
			stage.getIcons().add(new Image(iconInputStream));
		}
	}

	/**
	 * Loads an FXML file and returns the root parent node.
	 *
	 * @param fxmlPath the path to the FXML file.
	 * @return the root parent node of the loaded FXML file.
	 * @throws IOException if the FXML file cannot be loaded.
	 */
	public static Parent loadFXML(String fxmlPath) throws IOException {
		URL layoutURL = Launcher.class.getResource(fxmlPath);
		if (layoutURL == null) {
			throw new FileNotFoundException("Cannot find layout file: " + fxmlPath);
		}
		return FXMLLoader.load(layoutURL);
	}
}
