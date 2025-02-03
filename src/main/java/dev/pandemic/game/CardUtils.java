package dev.pandemic.game;

import dev.pandemic.dto.CardDTO;
import dev.pandemic.dto.CardListDTO;
import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.Path;
import dev.pandemic.model.Card;
import dev.pandemic.model.State;
import dev.pandemic.utilities.JAXBUtils;
import jakarta.xml.bind.JAXBException;

import java.util.ArrayList;
import java.util.Collections;

public class CardUtils {

    private CardUtils() {
    }

    public static ArrayList<Card> filterCards(CardType type) throws JAXBException {
        CardListDTO cardList = (CardListDTO) JAXBUtils.load(CardListDTO.class, Path.CARDS_CONFIG.getPath());
        var cardDtos = cardList.getCards();

        var cards = new ArrayList<Card>();

        // ModelMapper doesn't work here probably because of JAXB annotations
        for (CardDTO card : cardDtos) {
            if (card.type == type) {
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

    public static void fillAndShuffleDeck(State state, CardType type) throws JAXBException {
        var discardedCards = filterCards(type);
        Collections.shuffle(discardedCards);

        var cardDiscardPile = state.getCardDiscardPile();
        switch (type) {
            case CITY, EVENT -> {
                state.getPlayerCards().addAll(discardedCards);
                cardDiscardPile.removeIf(c -> c.getType() == type);
            }
            case INFECTION -> {
                state.setInfectionCards(discardedCards);
                cardDiscardPile.removeIf(c -> c.getType() == type);
            }
        }
    }

    public static ArrayList<Card> drawInfectionCards(State state){
        var infectionCards = state.getInfectionCards();
        var drawnCards = new ArrayList<Card>();
        for (int i = 0; i < state.getInfectionRateCounter(); i++) {
            drawnCards.add(infectionCards.getFirst());
            state.getCardDiscardPile().add(state.getInfectionCards().removeFirst());
        }

        return drawnCards;
    }
}
