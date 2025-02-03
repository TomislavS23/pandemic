package dev.pandemic.model;

import lombok.Getter;
import lombok.Setter;

public class GameState {
    private static GameState instance;
    @Getter
    @Setter
    private State state;

    private GameState() {
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
        }
        return instance;
    }
}
