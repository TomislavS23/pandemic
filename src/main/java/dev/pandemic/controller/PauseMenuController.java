package dev.pandemic.controller;

import dev.pandemic.utilities.AlertUtils;
import dev.pandemic.utilities.SceneLoader;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class PauseMenuController {
    private static final String GAME_VIEW_PATH = "/views/game-view.fxml";

    @FXML
    public VBox vbPauseMenu;
    @FXML
    public Button btnResume;
    @FXML
    public Button btnQuitGame;
    @FXML
    public Button btnNewGame;
    @FXML
    public Button btnSaveGame;
    @FXML
    public Button btnDocumentation;

    @FXML
    private void initialize() {
        initializeEvents();
        initializeButtonActions();
    }

    private void initializeEvents() {
        Platform.runLater(
                () -> vbPauseMenu.getScene().addEventFilter(KeyEvent.KEY_RELEASED, this::exitPauseMenu)
        );
    }

    private void initializeButtonActions() {
        btnResume.setOnAction(this::resumeGame);
        btnQuitGame.setOnAction(this::quitGame);
        btnNewGame.setOnAction(this::startNewGame);
    }

    @FXML
    public void resumeGame(ActionEvent event) {
        try {
            SceneLoader.loadScene(
                    GAME_VIEW_PATH,
                    (Stage) vbPauseMenu.getScene().getWindow(),
                    "Main Game",
                    false
            );
        } catch (IOException e) {
            AlertUtils.showAlert("Error", Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void startNewGame(ActionEvent event) {
        AlertUtils.showAlert("Not implemented yet.", "This button is not implemented.", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void quitGame(ActionEvent event) {
        if (AlertUtils.showConfirmationAlert("Exit Application", "Are you sure you want to exit?")) {
            Platform.exit();
        }
    }

    @FXML
    public void exitPauseMenu(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            try {
                SceneLoader.loadScene(
                        GAME_VIEW_PATH,
                        (Stage) vbPauseMenu.getScene().getWindow(),
                        "Main Game",
                        false
                );
            } catch (IOException e) {
                AlertUtils.showAlert("Error", Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
            }
        }
    }
}
