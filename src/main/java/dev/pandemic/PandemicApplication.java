package dev.pandemic;

import dev.pandemic.enumerations.FilePath;
import dev.pandemic.game.GameStateLoader;
import dev.pandemic.model.GameState;
import dev.pandemic.utilities.JAXBUtils;
import jakarta.xml.bind.JAXBException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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
       launch();
    }
}