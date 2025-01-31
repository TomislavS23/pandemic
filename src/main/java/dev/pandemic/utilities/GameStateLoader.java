package dev.pandemic.utilities;

import dev.pandemic.dto.CardDTO;
import dev.pandemic.dto.CardListDTO;
import dev.pandemic.enumerations.*;
import dev.pandemic.model.*;
import jakarta.xml.bind.JAXBException;
import javafx.collections.FXCollections;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class GameStateLoader {

    private static final int CUBE_COUNT = 24;

    private GameStateLoader() {
    }

    public static GameState prepareGameState() throws JAXBException {
        var gameState = (GameState) JAXBUtils.load(GameState.class, FilePath.GAME_STATE_CONFIG.getPath());
        gameState.setDiseaseCubes(initializeDiseaseCubes());
        gameState.setInfectionLevels(initializeInfectionLevels());
        gameState.setCityCards(initializeCards(CardType.CITY));
        gameState.setInfectionCards(initializeCards(CardType.INFECTION));
        gameState.setRoleCards(initializeCards(CardType.ROLE));
        gameState.setRoleCards(initializeCards(CardType.EVENT));
        gameState.setPlayerState(new Player());
        gameState.getPlayerState().setHand(FXCollections.observableArrayList());
        gameState.setCardDiscardPile(new ArrayList<>());

        return gameState;
    }

    private static ArrayList<InfectionLevel> initializeInfectionLevels() throws JAXBException {
        var infectionLevels = new ArrayList<InfectionLevel>();
        var cards = initializeCards(CardType.CITY);

        for (Card card : cards) {
            if (card.getType() == CardType.CITY) {
                infectionLevels.add(new InfectionLevel(card.getName(), 0));
            }
        }

        return infectionLevels;
    }

    public static ArrayList<Card> initializeCards(CardType type) throws JAXBException {
        CardListDTO cardList = (CardListDTO) JAXBUtils.load(CardListDTO.class, FilePath.CARDS_CONFIG.getPath());
        var cardDtos = cardList.getCards();

        var cards = new ArrayList<Card>();

        // ModelMapper doesn't work here probably because of JAXB annotations
        for (CardDTO card : cardDtos) {
            if (card.type == type){
                cards.add(new Card(
                        card.name,
                        card.description,
                        card.color,
                        card.type,
                        card.role,
                        card.eventType
                ));
            }
        }

        Collections.shuffle(cards);

        return cards;
    }

    private static ArrayList<DiseaseCube> initializeDiseaseCubes() {
        var cubes = new ArrayList<DiseaseCube>();
        cubes.add(new DiseaseCube(Color.RED, CUBE_COUNT));
        cubes.add(new DiseaseCube(Color.BLUE, CUBE_COUNT));
        return cubes;
    }
}
