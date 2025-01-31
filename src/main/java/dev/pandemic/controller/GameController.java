package dev.pandemic.controller;

import dev.pandemic.game.GameUtils;
import dev.pandemic.model.Card;
import dev.pandemic.model.GameState;
import dev.pandemic.utilities.AlertUtils;
import dev.pandemic.utilities.GameStateLoader;
import dev.pandemic.utilities.SceneLoader;
import jakarta.xml.bind.JAXBException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class GameController {
    private static final String PAUSE_MENU_VIEW_PATH = "/views/pause-menu-view.fxml";
    private static GameState state;
    private static boolean infectionCardsDrawn = false;

    @FXML
    public SplitPane splitPaneMain;
    @FXML
    public Label lbInfectionRate;
    @FXML
    public Label lbActionsLeftPlayer1;
    @FXML
    public Label lbActionsLeftPlayer2;
    @FXML
    public Label lbDiseaseCubesLeftP1;
    @FXML
    public Label lbCurrentPawnLocationP1;
    @FXML
    public Label lbDiseaseCubesLeftP2;
    @FXML
    public Label lbCurrentPawnLocationP2;
    @FXML
    public Button btnUseCard;
    @FXML
    public Label lbPlayer2Role;
    @FXML
    public Label lbPlayer1Role;
    @FXML
    public Button btnEndTurn;
    @FXML
    public Button btnDrawCityCard;
    @FXML
    public ListView lvCardsPlayer1;
    @FXML
    public ListView lvCardsPlayer2;
    @FXML
    public Button btnDrawInfectionCards;

    @FXML
    private void initialize() {
        initializeGameState();
        initializeEvents();
        initializeLabels();
        initializeLists();
    }

    private void initializeLists() {
        lvCardsPlayer1.setItems(state.getPlayerState().getHand());
    }

    private void initializeLabels() {
        lbInfectionRate.setText(String.valueOf(state.getInfectionRate()));
        lbActionsLeftPlayer1.setText(String.valueOf(state.getPlayerState().getActionsLeft()));
    }

    @FXML
    private void initializeGameState() {
        try {
            state = GameStateLoader.prepareGameState();
        } catch (JAXBException e) {
            AlertUtils.showAlert("Error", Arrays.toString(e.getStackTrace()), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void initializeEvents() {
        Platform.runLater(() -> {
            splitPaneMain.getScene().addEventHandler(KeyEvent.KEY_RELEASED, this::keyReleasedEvent);
            btnDrawInfectionCards.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawInfectionCards);
            btnDrawCityCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawCityCardClicked);
            btnUseCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::useCardClicked);
            btnEndTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::endTurnClicked);
        });
    }

    private void endTurnClicked(MouseEvent mouseEvent) {
        if (!infectionCardsDrawn) {
            AlertUtils.showAlert("Error", "You have to draw infection cards before finishing turn.", Alert.AlertType.WARNING);
            return;
        }

        infectionCardsDrawn = false;
    }

    private void drawCityCardClicked(MouseEvent mouseEvent) {
        var actions = state.getPlayerState().getActionsLeft();

        if (actions <= 0) {
            AlertUtils.showAlert("Error", "You have no actions left.", Alert.AlertType.WARNING);
            return;
        }

        state.getPlayerState().getHand().add(state.getCityCards().getFirst());
        state.getCardDiscardPile().add(state.getCityCards().getFirst());
        state.getCityCards().removeFirst();
        state.getPlayerState().setActionsLeft(--actions);

        lbActionsLeftPlayer1.setText(String.valueOf(actions));
    }

    private void drawInfectionCards(MouseEvent mouseEvent) {
        if (infectionCardsDrawn) {
            AlertUtils.showAlert("Error", "You have already drawn infection cards.", Alert.AlertType.WARNING);
            return;
        }

        var infectionCards = state.getInfectionCards();
        var drawnCards = new ArrayList<Card>();
        for (int i = 0; i < state.getInfectionRate(); i++) {
            drawnCards.add(infectionCards.get(i));
            state.getCardDiscardPile().add(infectionCards.get(i));
            state.getInfectionCards().remove(i);
        }

        GameUtils.applyInfectionCards(state, drawnCards);

        infectionCardsDrawn = true;
    }

    private void useCardClicked(MouseEvent mouseEvent) {
        var model = (Card) lvCardsPlayer1.getSelectionModel().getSelectedItem();

        if (model == null) AlertUtils.showAlert("Error", "No card selected.", Alert.AlertType.WARNING);

        System.out.println(model);
    }

    @FXML
    public void keyReleasedEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            try {
                SceneLoader.loadScene(
                        PAUSE_MENU_VIEW_PATH,
                        (Stage) splitPaneMain.getScene().getWindow(),
                        "Pause Menu",
                        false
                );
            } catch (IOException e) {
                AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }
}
