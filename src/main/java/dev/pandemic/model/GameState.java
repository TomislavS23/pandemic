package dev.pandemic.model;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlElementWrapper;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@NoArgsConstructor
@XmlRootElement(name = "game-state")
public class GameState {
    @XmlElement(name = "max-outbreaks")
    public int maxOutbreaks = 4;
    @XmlElement(name = "outbreak-counter")
    public int outbreakCounter = 0;
    @XmlElementWrapper(name = "disease-cubes")
    @XmlElement(name = "disease-cube")
    public ArrayList<DiseaseCube> diseaseCubes;
    @XmlElement(name = "starting-number-of-cards")
    public int startingNumberOfCards = 2;
    @XmlElement(name = "infection-rate")
    public int infectionRate = 2;
    @XmlElement(name = "infection-rate-counter")
    public int infectionRateCounter = 0;
    @XmlElement(name = "starting-infection-cards")
    public int startingInfectionCards = 3;
    @XmlElement(name = "epidemic-cards-in-player-deck")
    public int epidemicCardsInPlayerDeck = 4;
    @XmlElement(name = "player")
    public Player playerState;

    public GameState(ArrayList<DiseaseCube> diseaseCubes, Player playerState) {
        this.diseaseCubes = diseaseCubes;
        this.playerState = playerState;
    }
}


