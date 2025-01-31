package dev.pandemic.model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.flogger.Flogger;

import java.util.ArrayList;

@Setter
@NoArgsConstructor
@XmlRootElement(name = "game-state")
public class GameState {
    @XmlElement(name = "max-outbreaks")
    private int maxOutbreaks;
    @XmlElement(name = "outbreak-counter")
    private int outbreakCounter;
    @XmlElementWrapper(name = "disease-cubes")
    @XmlElement(name = "disease-cube")
    private ArrayList<DiseaseCube> diseaseCubes;
    @XmlElement(name = "starting-number-of-cards")
    private int startingNumberOfCards;
    @XmlElement(name = "infection-rate")
    private int infectionRate;
    @XmlElement(name = "infection-rate-counter")
    private int infectionRateCounter;
    @XmlElement(name = "starting-infection-cards")
    private int startingInfectionCards;
    @XmlElement(name = "epidemic-cards-in-player-deck")
    private int epidemicCardsInPlayerDeck;
    @XmlElement(name = "player")
    private Player playerState;
    @XmlElementWrapper(name = "cards")
    @XmlElement(name = "card")
    private ArrayList<Card> cards;
    @XmlElementWrapper(name = "discarded-cards")
    @XmlElement(name = "discarded-card")
    private ArrayList<Card> cardDiscardPile;
    @XmlElementWrapper(name = "infection-levels")
    @XmlElement(name = "infection-level")
    private ArrayList<InfectionLevel> infectionLevels;

    public GameState(
            ArrayList<DiseaseCube> diseaseCubes,
            ArrayList<InfectionLevel> infectionLevels,
            ArrayList<Card> cards,
            Player playerState
    ) {
        this.diseaseCubes = diseaseCubes;
        this.infectionLevels = infectionLevels;
        this.cards = cards;
        this.playerState = playerState;
    }

    @XmlTransient
    public int getMaxOutbreaks() {
        return maxOutbreaks;
    }

    @XmlTransient
    public int getOutbreakCounter() {
        return outbreakCounter;
    }

    @XmlTransient
    public ArrayList<DiseaseCube> getDiseaseCubes() {
        return diseaseCubes;
    }

    @XmlTransient
    public int getStartingNumberOfCards() {
        return startingNumberOfCards;
    }

    @XmlTransient
    public int getInfectionRate() {
        return infectionRate;
    }

    @XmlTransient
    public int getInfectionRateCounter() {
        return infectionRateCounter;
    }

    @XmlTransient
    public int getStartingInfectionCards() {
        return startingInfectionCards;
    }

    @XmlTransient
    public int getEpidemicCardsInPlayerDeck() {
        return epidemicCardsInPlayerDeck;
    }

    @XmlTransient
    public Player getPlayerState() {
        return playerState;
    }

    @XmlTransient
    public ArrayList<Card> getCards() {
        return cards;
    }

    @XmlTransient
    public ArrayList<Card> getCardDiscardPile() {
        return cardDiscardPile;
    }

    @XmlTransient
    public ArrayList<InfectionLevel> getInfectionLevels() {
        return infectionLevels;
    }
}


