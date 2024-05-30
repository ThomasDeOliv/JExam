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

public class FXHelpers {

    public static void setApplicationIcon(Stage stage, String iconPath) {
        InputStream iconInputStream = Launcher.class.getResourceAsStream(iconPath);
        if (iconInputStream != null) {
            stage.getIcons().add(new Image(iconInputStream));
        }
    }

    public static Parent loadFXML(String fxmlPath) throws IOException {
        URL layoutURL = Launcher.class.getResource(fxmlPath);
        if (layoutURL == null) {
            throw new FileNotFoundException("Cannot find layout file: " + fxmlPath);
        }
        return FXMLLoader.load(layoutURL);
    }

}
