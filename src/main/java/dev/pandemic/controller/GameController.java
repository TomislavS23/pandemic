package dev.pandemic.controller;

import dev.pandemic.utilities.AlertUtils;
import dev.pandemic.utilities.SceneLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    private static final String PAUSE_MENU_VIEW_PATH = "/views/pause-menu-view.fxml";

    @FXML
    public Pane pnMainGame;
    @FXML
    public ImageView ivMainGameImage;

    @FXML
    private void initialize() {
        initializeEvents();
    }

    @FXML
    public void enterPauseMenu(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            try {
                SceneLoader.loadScene(
                        PAUSE_MENU_VIEW_PATH,
                        (Stage) pnMainGame.getScene().getWindow(),
                        "Pause Menu",
                        false
                );
            } catch (IOException e) {
                e.getStackTrace();
                AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void initializeEvents() {
        Platform.runLater(() -> ivMainGameImage.getScene().addEventFilter(KeyEvent.KEY_RELEASED, this::enterPauseMenu));
    }
}
