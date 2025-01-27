package dev.pandemic.model;

import dev.pandemic.enumerations.CardEffect;
import dev.pandemic.enumerations.CardType;
import lombok.Data;

@Data
public class Card {
    private String name;
    private CardType cardType;
    private String description;
    private CardEffect cardEffect;
}
