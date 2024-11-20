package dev.pandemic.application.controller;

import dev.pandemic.application.applicationutils.SceneLoader;
import dev.pandemic.service.utilities.AlertUtils;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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
    public Label lbTitle;
    @FXML
    public Button btnResume;
    @FXML
    public Button btnSettings;
    @FXML
    public Button btnLeaveMatch;
    @FXML
    public Button btnQuitGame;
    @FXML
    public Button btnNewGame;
    @FXML
    public Button btnStatistics;

    @FXML
    private void initialize() {
        initializeEvents();
    }

    private void initializeEvents() {
        Platform.runLater(
                () -> vbPauseMenu.getScene().addEventFilter(KeyEvent.KEY_RELEASED, this::exitPauseMenu)
        );

        initializeButtonActions();
    }

    private void initializeButtonActions() {
        btnResume.setOnAction(this::resumeGame);
        btnLeaveMatch.setOnAction(this::leaveMatch);
        btnQuitGame.setOnAction(this::quitGame);
        btnNewGame.setOnAction(this::startNewGame);
        btnStatistics.setOnAction(this::displayStatistics);
        btnSettings.setOnAction(this::displaySettings);
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
    public void displaySettings(ActionEvent event) {
        AlertUtils.showAlert("Not implemented yet.", "This button is not implemented.", Alert.AlertType.INFORMATION);
    }

    @FXML
    public void leaveMatch(ActionEvent event) {
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

    @FXML
    public void displayStatistics(ActionEvent event) {
        AlertUtils.showAlert("Not implemented yet.", "This button is not implemented.", Alert.AlertType.INFORMATION);
    }
}
