package dev.pandemic.model;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class GameState {
    private int infectionRate;
    private int outbreaks;
    private Map<String, Boolean> cures;
    private Map<String, Integer> diseaseCubes;
    private Deck infectionDeck;
    private Deck playerDeck;
    private List<Player> players;
}
