package dev.pandemic.application.controller;

import dev.pandemic.application.PandemicApplication;
import dev.pandemic.service.utilities.AlertUtils;
import dev.pandemic.application.applicationutils.SceneLoader;
import dev.pandemic.service.utilities.MessageUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController {
    private static final String GAME_VIEW_PATH = "/views/game-view.fxml";

    @FXML
    public Button btnStartGame;
    @FXML
    public Button btnLoadGame;
    @FXML
    public Button btnSettings;
    @FXML
    public Button btnExit;
    @FXML
    public VBox vbMenu;

    @FXML
    public void startGame(ActionEvent event) {
        try {
            SceneLoader.loadScene(
                    GAME_VIEW_PATH,
                    (Stage) btnStartGame.getScene().getWindow(),
                    "Main Game",
                    false);
        } catch (IOException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void loadGame(ActionEvent event) {
    }

    @FXML
    public void enterSettings(ActionEvent event) {
    }

    @FXML
    public void exitGame(ActionEvent event) {
        if (AlertUtils.showConfirmationAlert("Exit Application", "Are you sure you want to exit?")) {
            Platform.exit();
        }
    }
}
