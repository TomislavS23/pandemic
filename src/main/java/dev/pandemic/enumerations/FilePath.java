package dev.pandemic.enumerations;

import lombok.Getter;

@Getter
public enum FilePath {
    CARDS_CONFIG("src/main/resources/config/cards.xml"),
    GAME_STATE_CONFIG("src/main/resources/config/game-state.xml");


    private final String path;

    FilePath(String path) {
        this.path = path;
    }
}
