package dev.pandemic.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;

@NoArgsConstructor
@Setter
public class Deck implements Serializable {
    private ArrayList<Card> cards;
}
