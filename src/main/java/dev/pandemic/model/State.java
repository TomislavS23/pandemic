package dev.pandemic.model;

import jakarta.xml.bind.annotation.*;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@Setter
@NoArgsConstructor
@XmlRootElement(name = "game-state")
public class State implements Serializable {
    @XmlElement(name = "max-outbreaks")
    private int maxOutbreaks;
    @XmlElement(name = "cube-number-for-outbreak")
    private int cubeNumberForOutbreak;
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
    @XmlElement(name = "player-01")
    private Player player01State;
    @XmlElement(name = "player-02")
    private Player player02State;
    @XmlElementWrapper(name = "player-cards")
    @XmlElement(name = "card")
    private ArrayList<Card> playerCards;
    @XmlElementWrapper(name = "role-deck-cards")
    @XmlElement(name = "card")
    private ArrayList<Card> roleCards;
    @XmlElementWrapper(name = "infection-deck-cards")
    @XmlElement(name = "card")
    private ArrayList<Card> infectionCards;
    @XmlElementWrapper(name = "discarded-cards")
    @XmlElement(name = "discarded-card")
    private ArrayList<Card> cardDiscardPile;
    @XmlElementWrapper(name = "infection-levels")
    @XmlElement(name = "infection-level")
    private ArrayList<InfectionLevel> infectionLevels;

    public State(
            ArrayList<DiseaseCube> diseaseCubes,
            ArrayList<InfectionLevel> infectionLevels,
            ArrayList<Card> playerCards,
            ArrayList<Card> roleCards,
            ArrayList<Card> infectionCards,
            Player player01State,
            Player player02State
    ) {
        this.diseaseCubes = diseaseCubes;
        this.infectionLevels = infectionLevels;
        this.playerCards = playerCards;
        this.roleCards = roleCards;
        this.infectionCards = infectionCards;
        this.player01State = player01State;
        this.player02State = player02State;
    }

    @XmlTransient
    public int getMaxOutbreaks() {
        return maxOutbreaks;
    }

    @XmlTransient
    public int getCubeNumberForOutbreak() {
        return cubeNumberForOutbreak;
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
    public Player getPlayer01State() {
        return player01State;
    }

    @XmlTransient
    public ArrayList<Card> getPlayerCards() {
        return playerCards;
    }

    @XmlTransient
    public ArrayList<Card> getRoleCards() {
        return roleCards;
    }

    @XmlTransient
    public ArrayList<Card> getInfectionCards() {
        return infectionCards;
    }

    @XmlTransient
    public ArrayList<Card> getCardDiscardPile() {
        return cardDiscardPile;
    }

    @XmlTransient
    public ArrayList<InfectionLevel> getInfectionLevels() {
        return infectionLevels;
    }

    @XmlTransient
    public Player getPlayer02State() {
        return player02State;
    }
}


