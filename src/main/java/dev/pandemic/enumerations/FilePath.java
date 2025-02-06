package dev.pandemic.enumerations;

import lombok.Getter;

@Getter
public enum FilePath {
    CARDS_CONFIG("src/main/resources/config/cards.xml"),
    GAME_STATE_CONFIG("src/main/resources/config/game-state.xml"),
    GAME_STATE_OUTPUT("src/main/resources/output/game-state.xml"),
    SERIALIZED_GAME_STATE("src/main/resources/output/serialized-game-state.txt"),
    PATH_WITH_CLASSES("target/classes/"),
    DOCUMENTATION_PATH("src/main/resources/output/documentation.html");

    private final String path;

    FilePath(String path) {
        this.path = path;
    }
}
