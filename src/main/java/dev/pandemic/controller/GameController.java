package dev.pandemic.controller;

import dev.pandemic.PandemicApplication;
import dev.pandemic.utilities.AlertUtils;
import dev.pandemic.utilities.SceneLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameController {

    private static final String PAUSE_MENU_VIEW_PATH = "/views/pause-menu-view.fxml";
    private static Boolean DEBUGGER_VISIBLE = false;

    @FXML
    public SplitPane splitPaneMain;
    @FXML
    public StackPane stackPaneDebugger;

    @FXML
    private void initialize() {
        initializeEvents();
    }

    @FXML
    public void keyReleasedEvent(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case ESCAPE -> {
                try {
                    SceneLoader.loadScene(
                            PAUSE_MENU_VIEW_PATH,
                            (Stage) splitPaneMain.getScene().getWindow(),
                            "Pause Menu",
                            false
                    );
                } catch (IOException e) {
                    e.getStackTrace();
                    AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
                }
            }
            case F1 -> {
                DEBUGGER_VISIBLE = !DEBUGGER_VISIBLE;
                stackPaneDebugger.setVisible(DEBUGGER_VISIBLE);
            }
        }
    }

    @FXML
    private void initializeEvents() {
        Platform.runLater(() -> splitPaneMain.getScene().addEventFilter(KeyEvent.KEY_RELEASED, this::keyReleasedEvent));
    }
}
