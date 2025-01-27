package dev.pandemic.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Deck {
    public ArrayList<Card> cards;

    public Deck(ArrayList<Card> cards) {
        this.cards = cards;
    }
}
