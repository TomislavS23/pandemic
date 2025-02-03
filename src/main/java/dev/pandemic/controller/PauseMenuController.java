package dev.pandemic.controller;

import dev.pandemic.enumerations.FilePath;
import dev.pandemic.game.GameStateLoader;
import dev.pandemic.model.GameState;
import dev.pandemic.utilities.AlertUtils;
import dev.pandemic.utilities.JAXBUtils;
import dev.pandemic.utilities.SceneLoader;
import jakarta.xml.bind.JAXBException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
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
        btnResume.addEventFilter(MouseEvent.MOUSE_CLICKED, this::resumeGame);
        btnNewGame.addEventFilter(MouseEvent.MOUSE_CLICKED, this::startNewGame);
        btnSaveGame.addEventFilter(MouseEvent.MOUSE_CLICKED, this::saveGame);
        btnQuitGame.addEventFilter(MouseEvent.MOUSE_CLICKED, this::quitGame);
    }

    private void saveGame(MouseEvent mouseEvent) {
        try {
            var state = GameState.getInstance().getState();
            state.getPlayerState().preSave();

            JAXBUtils.save(state, FilePath.GAME_STATE_OUTPUT.getPath());
            AlertUtils.showAlert("Error", "File saved!", Alert.AlertType.CONFIRMATION);
        } catch (JAXBException e) {
            AlertUtils.showAlert("Error", Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void resumeGame(MouseEvent event) {
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
    public void startNewGame(MouseEvent event) {
        try {
            GameState.getInstance().setState(GameStateLoader.prepareGameState());
            SceneLoader.loadScene(
                    GAME_VIEW_PATH,
                    (Stage) btnNewGame.getScene().getWindow(),
                    "Main Game",
                    false);
        } catch (IOException | JAXBException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void quitGame(MouseEvent event) {
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
