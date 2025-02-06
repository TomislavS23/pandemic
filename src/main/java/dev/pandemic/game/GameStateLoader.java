package dev.pandemic.game;

import dev.pandemic.enumerations.*;
import dev.pandemic.model.*;
import dev.pandemic.utilities.serialization.JAXBUtils;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;

public class GameStateLoader {

    private static final int CUBE_COUNT = 24;

    private GameStateLoader() {
    }

    public static State prepareGameState() throws JAXBException {
        var gameState = (State) JAXBUtils.load(State.class, FilePath.GAME_STATE_CONFIG.getPath());

        var playerCards = CardUtils.filterCards(CardType.CITY);
        playerCards.addAll(CardUtils.filterCards(CardType.EVENT));
        Collections.shuffle(playerCards);
        setupGameState(gameState, playerCards);

        var player01State = gameState.getPlayer01State();
        var player02State = gameState.getPlayer02State();
        setupPlayerState(player01State, player02State);

        return gameState;
    }

    private static void setupPlayerState(Player player01State, Player player02State) {
        player01State.setPawn(new Pawn(City.LOS_ANGELES));
        player01State.setHand(FXCollections.observableArrayList());
        player01State.setRole(Role.SCIENTIST);
        player01State.setAbility(RoleAbility.CURE_DISEASE);
        player01State.setActionsLeft(4);

        player02State.setPawn(new Pawn(City.LOS_ANGELES));
        player02State.setHand(FXCollections.observableArrayList());
        player02State.setRole(Role.MEDIC);
        player02State.setAbility(RoleAbility.TREAT_DISEASE);
        player02State.setActionsLeft(4);
    }

    private static void setupGameState(State gameState, ArrayList<Card> playerCards) throws JAXBException {
        gameState.setDiseaseCubes(initializeDiseaseCubes());
        gameState.setInfectionLevels(initializeInfectionLevels());
        gameState.setPlayerCards(playerCards);
        gameState.setInfectionCards(CardUtils.filterCards(CardType.INFECTION));
        gameState.setRoleCards(CardUtils.filterCards(CardType.ROLE));
        gameState.setPlayer01State(new Player());
        gameState.setPlayer02State(new Player());
        gameState.setCardDiscardPile(new ArrayList<>());
    }

    private static ArrayList<InfectionLevel> initializeInfectionLevels() throws JAXBException {
        var infectionLevels = new ArrayList<InfectionLevel>();
        var cards = CardUtils.filterCards(CardType.CITY);

        for (Card card : cards) {
            if (card.getType() == CardType.CITY) {
                infectionLevels.add(new InfectionLevel(card.getName(), 0, false));
            }
        }

        return infectionLevels;
    }

    private static ArrayList<DiseaseCube> initializeDiseaseCubes() {
        var cubes = new ArrayList<DiseaseCube>();
        cubes.add(new DiseaseCube(Color.RED, CUBE_COUNT));
        cubes.add(new DiseaseCube(Color.BLUE, CUBE_COUNT));
        return cubes;
    }
}
