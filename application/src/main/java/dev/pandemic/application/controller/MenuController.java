package dev.pandemic.application.controller;

import dev.pandemic.application.PandemicApplication;
import dev.pandemic.service.utilities.AlertUtils;
import dev.pandemic.service.utilities.MessageUtils;
import dev.pandemic.service.utilities.SceneLoader;
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

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class MenuController implements Initializable {

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
                    new FXMLLoader(PandemicApplication.class.getResource("/views/game-view.fxml")),
                    (Stage) btnStartGame.getScene().getWindow()
            );
        } catch (IOException e) {
            System.out.println(e.getMessage());
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }
}
