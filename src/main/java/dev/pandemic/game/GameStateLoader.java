package dev.pandemic.game;

import dev.pandemic.enumerations.*;
import dev.pandemic.model.*;
import dev.pandemic.utilities.JAXBUtils;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GameStateLoader {

    private static final int CUBE_COUNT = 24;

    private GameStateLoader() {
    }

    public static GameState prepareGameState() throws JAXBException {
        var gameState = (GameState) JAXBUtils.load(GameState.class, FilePath.GAME_STATE_CONFIG.getPath());

        var playerCards = CardUtils.filterCards(CardType.CITY);
        playerCards.addAll(CardUtils.filterCards(CardType.EVENT));
        Collections.shuffle(playerCards);

        gameState.setDiseaseCubes(initializeDiseaseCubes());
        gameState.setInfectionLevels(initializeInfectionLevels());
        gameState.setPlayerCards(playerCards);
        gameState.setInfectionCards(CardUtils.filterCards(CardType.INFECTION));
        gameState.setRoleCards(CardUtils.filterCards(CardType.ROLE));
        gameState.setPlayerState(new Player());
        gameState.getPlayerState().setPawn(new Pawn(City.LOS_ANGELES));
        gameState.getPlayerState().setHand(FXCollections.observableArrayList());
        gameState.setCardDiscardPile(new ArrayList<>());

        return gameState;
    }

    private static ArrayList<InfectionLevel> initializeInfectionLevels() throws JAXBException {
        var infectionLevels = new ArrayList<InfectionLevel>();
        var cards = CardUtils.filterCards(CardType.CITY);

        for (Card card : cards) {
            if (card.getType() == CardType.CITY) {
                infectionLevels.add(new InfectionLevel(card.getName(), 0));
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
