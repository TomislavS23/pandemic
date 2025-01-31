package dev.pandemic.game;

import dev.pandemic.model.Card;
import dev.pandemic.model.GameState;
import dev.pandemic.model.InfectionLevel;

import java.util.List;
import java.util.Optional;

public class GameUtils {
    private GameUtils() {
    }

    public static void applyInfectionCards(GameState state, List<Card> cards) {
        for (Card card : cards) {
            var cityName = card.getName();
            var infectionLevel = state.getInfectionLevels().stream().filter(il -> cityName.equals(il.getCity())).findFirst();
            setInfectionLevel(infectionLevel);
        }
    }

    private static void setInfectionLevel(Optional<InfectionLevel> city) {
        city.ifPresent(il -> {
            var level = il.getLevel();
            il.setLevel(++level);
        });
    }
}
