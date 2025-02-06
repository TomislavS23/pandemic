package dev.pandemic.fxutilities;

import dev.pandemic.PandemicApplication;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {
    private SceneLoader() {
    }

    /**
     * Sets new scene to already active stage.
     *
     * @param view path to the view
     * @param stage defines the stage for specific scene
     * @param title window title
     * @param resizeable decides if window should be resizeable for that scene
     * @throws IOException
     */
    public static void loadScene(String view, Stage stage, String title, Boolean resizeable) throws IOException {
        var loader = new FXMLLoader(PandemicApplication.class.getResource(view));
        stage.setTitle(title);
        stage.setResizable(resizeable);
        stage.setScene(new Scene(loader.load()));
    }
}