package dev.pandemic.game;

import dev.pandemic.enumerations.*;
import dev.pandemic.model.*;
import dev.pandemic.utilities.JAXBUtils;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;

public class GameStateLoader {

    private static final int CUBE_COUNT = 24;

    private GameStateLoader() {
    }

    public static State prepareGameState() throws JAXBException {
        var gameState = (State) JAXBUtils.load(State.class, Path.GAME_STATE_CONFIG.getPath());

        var playerCards = CardUtils.filterCards(CardType.CITY);
        playerCards.addAll(CardUtils.filterCards(CardType.EVENT));
        Collections.shuffle(playerCards);

        gameState.setDiseaseCubes(initializeDiseaseCubes());
        gameState.setInfectionLevels(initializeInfectionLevels());
        gameState.setPlayerCards(playerCards);
        gameState.setInfectionCards(CardUtils.filterCards(CardType.INFECTION));
        gameState.setRoleCards(CardUtils.filterCards(CardType.ROLE));
        gameState.setPlayerState(new Player());

        var playerState = gameState.getPlayerState();

        playerState.setPawn(new Pawn(City.LOS_ANGELES));
        playerState.setHand(FXCollections.observableArrayList());
        playerState.setRole(Role.SCIENTIST);
        playerState.setAbility(RoleAbility.CURE_DISEASE);
        playerState.setActionsLeft(4);
        gameState.setCardDiscardPile(new ArrayList<>());

        return gameState;
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
