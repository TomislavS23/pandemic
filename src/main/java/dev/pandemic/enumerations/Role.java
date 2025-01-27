package dev.pandemic.enumerations;

import lombok.Getter;

public enum Role {
    MEDIC("Medic"),
    SCIENTIST("Scientist"),
    RESEARCHER("Researcher"),
    DISPATCHER("Dispatcher");

    @Getter
    private final String name;

    Role(String name) {
        this.name = name;
    }
}
