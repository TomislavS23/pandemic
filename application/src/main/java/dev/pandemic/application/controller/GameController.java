package dev.pandemic.application.controller;

import dev.pandemic.application.applicationutils.SceneLoader;
import dev.pandemic.service.utilities.AlertUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    private static final String PAUSE_MENU_VIEW_PATH = "/views/pause-menu-view.fxml";

    @FXML
    public Pane pnMainGame;
    @FXML
    public ImageView ivMainGameImage;

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
                AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Really stupid fix for onKeyReleased event that is not working on Pane element.
        pnMainGame.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyReleased(this::enterPauseMenu);
            }
        });
    }
}
