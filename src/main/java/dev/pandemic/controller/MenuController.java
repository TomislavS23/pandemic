package dev.pandemic.controller;

import dev.pandemic.documentation.DocumentationUtils;
import dev.pandemic.enumerations.FilePath;
import dev.pandemic.model.GameState;
import dev.pandemic.model.State;
import dev.pandemic.fxutilities.AlertUtils;
import dev.pandemic.utilities.serialization.JAXBUtils;
import dev.pandemic.fxutilities.SceneLoader;
import dev.pandemic.utilities.serialization.Serialization;
import jakarta.xml.bind.JAXBException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuController {
    private static final String GAME_VIEW_PATH = "/views/game-view.fxml";

    @FXML
    public Button btnStartGame;
    @FXML
    public Button btnLoadGame;
    @FXML
    public Button btnDocumentation;
    @FXML
    public Button btnExit;
    @FXML
    public VBox vbMenu;

    @FXML
    private void initialize() {
        initializeEvents();
    }

    private void initializeEvents() {
        btnStartGame.setOnAction(this::startGame);
        btnLoadGame.setOnAction(this::loadGame);
        btnDocumentation.setOnAction(this::printDocs);
        btnExit.setOnAction(this::exitGame);
    }

    @FXML
    private void printDocs(ActionEvent actionEvent) {
        try {
            DocumentationUtils.generateHtmlDocumentationFile();
            AlertUtils.showAlert("Generated", "Your documentation has been generated!", Alert.AlertType.INFORMATION);
        } catch (IOException | ClassNotFoundException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

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
        try {
            Serialization.deserialize(FilePath.SERIALIZED_GAME_STATE.getPath(), FilePath.GAME_STATE_OUTPUT.getPath());
            var state = (State) JAXBUtils.load(State.class, FilePath.GAME_STATE_OUTPUT.getPath());
            state.getPlayer01State().postLoad();
            GameState.getInstance().setState(state);

            SceneLoader.loadScene(
                    GAME_VIEW_PATH,
                    (Stage) btnStartGame.getScene().getWindow(),
                    "Main Game",
                    false);
        } catch (IOException | JAXBException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void exitGame(ActionEvent event) {
        if (AlertUtils.showConfirmationAlert("Exit Application", "Are you sure you want to exit?")) {
            Platform.exit();
        }
    }
}
