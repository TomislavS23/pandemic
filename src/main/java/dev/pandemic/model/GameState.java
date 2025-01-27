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
@XmlType(propOrder = {
        "maxOutbreaks",
        "outbreakCounter",
        "diseaseCubes",
        "startingNumberOfCards",
        "infectionRate",
        "infectionRateCounter",
        "startingInfectionCards",
        "epidemicCardsInPlayerDeck"}
)
public class GameState {
    public int maxOutbreaks = 4;
    public int outbreakCounter = 0;
    @XmlElementWrapper(name = "disease-cubes")
    @XmlElement(name = "disease-cube")
    public ArrayList<DiseaseCube> diseaseCubes;
    public int startingNumberOfCards = 2;
    public int infectionRate = 2;
    public int infectionRateCounter = 0;
    public int startingInfectionCards = 3;
    public int epidemicCardsInPlayerDeck = 4;

    public GameState(ArrayList<DiseaseCube> diseaseCubes) {
        this.diseaseCubes = diseaseCubes;
    }
}


