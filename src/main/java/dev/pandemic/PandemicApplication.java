package dev.pandemic;

import dev.pandemic.model.GameState;
import dev.pandemic.utilities.ConfigLoader;
import dev.pandemic.utilities.JAXBUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class PandemicApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PandemicApplication.class.getResource("/views/menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Pandemic Board Game");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        try {
            GameState gameState = ConfigLoader.prepareGameConfig();
            JAXBUtils.save(gameState, "state.xml");

            var result = JAXBUtils.load(GameState.class, "state.xml");
            System.out.println(result);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        launch();
    }
}