package dev.pandemic.fxutilities;

import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.PlayerType;
import dev.pandemic.game.CardUtils;
import dev.pandemic.game.GameUtils;
import dev.pandemic.model.Card;
import dev.pandemic.model.Player;
import dev.pandemic.model.State;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;
import java.util.Map;

public class FXUtils {
    private FXUtils() {

    }

    public static void disableButtons(Boolean disabled, ArrayList<Button> buttons) {
        for (Button button : buttons) {
            button.setDisable(disabled);
        }
    }

    public static void handleCardSelection(State state, ListView<Card> lvCards, PlayerType playerType, Label lbCurrentPawnLocation, Label lbInfectionRate) throws JAXBException {
        Player playerState;
        var card = lvCards.getSelectionModel().getSelectedItem();

        switch (playerType) {
            case PLAYER_01 -> playerState = state.getPlayer01State();
            case PLAYER_02 -> playerState = state.getPlayer02State();
            default -> throw new IllegalStateException("Unexpected value: " + playerType);
        }

        if (validateCardSelection(playerState, card)) {
            return;
        }

        processCardSelection(state, playerState, card, lvCards, lbCurrentPawnLocation, lbInfectionRate, playerType);
    }

    public static void handleCardSelection(State state, ListView<Card> lvCards, Label lbCurrentPawnLocationP1, Label lbInfectionRate) throws JAXBException {
        var playerState = state.getPlayer01State();
        var card = lvCards.getSelectionModel().getSelectedItem();

        if (validateCardSelection(playerState, card)) {
            return;
        }

        processCardSelection(state, playerState, card, lvCards, lbCurrentPawnLocationP1, lbInfectionRate, PlayerType.PLAYER_01);
    }

    private static boolean validateCardSelection(Player playerState, Card card) {
        if (card == null) {
            AlertUtils.showAlert("Error", "No card selected.", Alert.AlertType.WARNING);
            return true;
        }

        if (playerState.getActionsLeft() <= 0 && card.getType() != CardType.EVENT) {
            AlertUtils.showAlert("Error", "You have no actions left.", Alert.AlertType.WARNING);
            return true;
        }

        return false;
    }

    private static void processCardSelection(State state, Player playerState, Card card,
                                             ListView<Card> lvCards, Label lbCurrentPawnLocation, Label lbInfectionRate, PlayerType playerType) throws JAXBException {
        playerState.getHand().remove(card);
        state.getCardDiscardPile().add(card);
        GameUtils.applyPlayerCard(state, card, lbCurrentPawnLocation, playerType);
        lvCards.getItems().remove(card);
        playerState.setActionsLeft(playerState.getActionsLeft() - 1);
        lbInfectionRate.setText(String.valueOf(state.getInfectionRateCounter()));
        playerState.preSave();
    }

    public static void handleDrawPlayerCard(State state, Label lbActionsLeft, PlayerType playerType) throws JAXBException {
        var playerState = processDrawPlayerCard(state, lbActionsLeft, playerType);
        if (playerState == null) return;

        playerState.preSave();
    }

    private static Player processDrawPlayerCard(State state, Label lbActionsLeft, PlayerType playerType) throws JAXBException {
        switch (playerType) {
            case SINGLEPLAYER, PLAYER_01 -> {
                var playerState = state.getPlayer01State();
                var actions = state.getPlayer01State().getActionsLeft();

                return processData(state, lbActionsLeft, playerState, actions);
            }
            case PLAYER_02 -> {
                var playerState = state.getPlayer02State();
                var actions = state.getPlayer02State().getActionsLeft();

                return processData(state, lbActionsLeft, playerState, actions);
            }
        }

        return null;
    }

    private static Player processData(State state, Label lbActionsLeft, Player playerState, int actions) throws JAXBException {
        if (actions <= 0) {
            AlertUtils.showAlert("Error", "You have no actions left.", Alert.AlertType.WARNING);
            return null;
        }

        var playerCards = state.getPlayerCards();
        if (playerCards.isEmpty()) CardUtils.fillAndShuffleDeck(state, CardType.CITY);

        var drawnCard = playerCards.removeFirst();

        if (playerState.getHand() == null) {
            playerState.setHand(FXCollections.observableArrayList());
        }

        playerState.getHand().add(drawnCard);
        playerState.setActionsLeft(--actions);
        lbActionsLeft.setText(String.valueOf(actions));
        return playerState;
    }

    public static void setFieldValues(State state, Map<Label, Label> fields) {
        for (Map.Entry<Label, Label> entry : fields.entrySet()) {
            var infectionLevel = state.getInfectionLevels().stream()
                    .filter(c -> c.getCity().equals(entry.getKey().getText())).findFirst();

            infectionLevel.ifPresent(il -> {
                if (il.isDiseaseCured()) {
                    return;
                }
                entry.getValue().setText(String.valueOf(il.getLevel()));
            });
        }
    }
}
