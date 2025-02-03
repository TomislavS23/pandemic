package dev.pandemic.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

@NoArgsConstructor
@Setter
public class Deck {
    private ArrayList<Card> cards;
}
