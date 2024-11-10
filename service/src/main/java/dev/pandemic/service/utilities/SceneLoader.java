package dev.pandemic.service.utilities;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class SceneLoader {
    private SceneLoader() {
    }

    public static void loadScene(FXMLLoader view, Stage stage) throws IOException {
        stage.setScene(new Scene(view.load()));
    }
}