package dev.pandemic.controller;

import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.Color;
import dev.pandemic.game.CardUtils;
import dev.pandemic.game.GameUtils;
import dev.pandemic.model.Card;
import dev.pandemic.model.DiseaseCube;
import dev.pandemic.model.GameState;
import dev.pandemic.utilities.AlertUtils;
import dev.pandemic.game.GameStateLoader;
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
import java.util.HashMap;
import java.util.Map;

public class GameController {
    private static final String PAUSE_MENU_VIEW_PATH = "/views/pause-menu-view.fxml";
    private static GameState state;
    private static boolean infectionCardsDrawn = false;
    private static final Map<Label, Label> fields = new HashMap<>();

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
    public Button btnDrawCard;
    @FXML
    public ListView lvCardsPlayer1;
    @FXML
    public ListView lvCardsPlayer2;
    @FXML
    public Button btnDrawInfectionCards;
    @FXML
    public Label lbLA;
    @FXML
    public Label lbMC;
    @FXML
    public Label lbBogota;
    @FXML
    public Label lbBuenosAries;
    @FXML
    public Label lbLondon;
    @FXML
    public Label lbStPetersburg;
    @FXML
    public Label lbIstanbul;
    @FXML
    public Label lbShanghai;
    @FXML
    public Label lbInfectionLevelLA;
    @FXML
    public Label lbInfectionLevelMC;
    @FXML
    public Label lbInfectionLevelBogota;
    @FXML
    public Label lbInfectionLevelBA;
    @FXML
    public Label lbInfectionLevelLondon;
    @FXML
    public Label lbInfectionLevelSP;
    @FXML
    public Label lbInfectionLevelIstanbul;
    @FXML
    public Label lbInfectionLevelShanghai;

    @FXML
    private void initialize() {
        initializeGameState();
        initializeEvents();
        initializeFields();
        initializeLists();
    }

    private void initializeLists() {
        lvCardsPlayer1.setItems(state.getPlayerState().getHand());
    }

    private void initializeFields() {
        initCityFields();
        initStatusFields();
    }

    private void initStatusFields() {
        var diseaseCubeCount = state.getDiseaseCubes().stream()
                .filter(dc -> dc.getColor() == Color.BLUE).findFirst();
        lbInfectionRate.setText(String.valueOf(state.getInfectionRate()));
        lbActionsLeftPlayer1.setText(String.valueOf(state.getPlayerState().getActionsLeft()));
        lbCurrentPawnLocationP1.setText(state.getPlayerState().getPawn().getLocation().getCityName());
        diseaseCubeCount.ifPresent(dc -> lbDiseaseCubesLeftP1.setText(String.valueOf(dc.getCount())));
    }

    private void initCityFields() {
        fields.put(lbLA, lbInfectionLevelLA);
        fields.put(lbMC, lbInfectionLevelMC);
        fields.put(lbBogota, lbInfectionLevelBogota);
        fields.put(lbBuenosAries, lbInfectionLevelBA);
        fields.put(lbLondon, lbInfectionLevelLondon);
        fields.put(lbStPetersburg, lbInfectionLevelSP);
        fields.put(lbIstanbul, lbInfectionLevelIstanbul);
        fields.put(lbShanghai, lbInfectionLevelShanghai);
    }

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
            btnDrawCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawPlayerCard);
            btnUseCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::useCard);
            btnEndTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::endTurn);
        });
    }

    @FXML
    private void endTurn(MouseEvent mouseEvent) {
        if (!infectionCardsDrawn) {
            AlertUtils.showAlert("Error", "You have to draw infection cards before finishing turn.", Alert.AlertType.WARNING);
            return;
        }

        if (GameUtils.hasUnusedEventCards(state)) {
            AlertUtils.showAlert("Error", "Cannot finish this turn! You have unused event cards.", Alert.AlertType.WARNING);
            return;
        }

        infectionCardsDrawn = false;
        btnDrawInfectionCards.setDisable(infectionCardsDrawn);
    }

    @FXML
    private void drawPlayerCard(MouseEvent mouseEvent) {
        try {
            var playerState = state.getPlayerState();
            var actions = state.getPlayerState().getActionsLeft();

            if (actions <= 0) {
                AlertUtils.showAlert("Error", "You have no actions left.", Alert.AlertType.WARNING);
                return;
            }

            var playerCards = state.getPlayerCards();
            if (playerCards.isEmpty()) CardUtils.fillAndShuffleDeck(state, CardType.CITY);

            var drawnCard = playerCards.removeFirst();
            playerState.getHand().add(drawnCard);
            state.getCardDiscardPile().add(drawnCard);
            playerState.setActionsLeft(--actions);
            lbActionsLeftPlayer1.setText(String.valueOf(actions));
        } catch (JAXBException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void drawInfectionCards(MouseEvent mouseEvent) {
        try {
            if (state.getInfectionCards().isEmpty() || state.getInfectionRateCounter() > (long) state.getInfectionCards().size()) {
                CardUtils.fillAndShuffleDeck(state, CardType.INFECTION);
            }

            GameUtils.applyInfectionCards(state, CardUtils.drawInfectionCards(state));
            GameUtils.applyInfectionLevels(state, fields);

            infectionCardsDrawn = true;
            btnDrawInfectionCards.setDisable(infectionCardsDrawn);

            if (GameUtils.applyOutbreakLoseCondition(state))
                AlertUtils.showAlert("Game Lost", "You have lost the game.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void useCard(MouseEvent mouseEvent) {
        var card = (Card) lvCardsPlayer1.getSelectionModel().getSelectedItem();

        if (card == null) {
            AlertUtils.showAlert("Error", "No card selected.", Alert.AlertType.WARNING);
        }


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
