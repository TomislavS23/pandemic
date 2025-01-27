package dev.pandemic.model;

import dev.pandemic.enumerations.CardColor;
import dev.pandemic.enumerations.CardEventType;
import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.RoleAbility;
import lombok.Data;

import java.util.Optional;

@Data
public class Card {
    public String name;
    public Optional<String> description;
    public Optional<CardColor> color;
    public CardType type;
    public Optional<CardEventType> eventType;
}
