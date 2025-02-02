package dev.pandemic.game;

import dev.pandemic.enumerations.CardType;
import dev.pandemic.model.Card;
import dev.pandemic.model.DiseaseCube;
import dev.pandemic.model.GameState;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Map;

public class GameUtils {
    private GameUtils() {
    }

    public static void applyInfectionCards(GameState state, List<Card> cards) {
        for (Card card : cards) {
            var cityName = card.getName();
            var infectionLevel = state.getInfectionLevels().stream()
                    .filter(il -> cityName.equals(il.getCity())).findFirst();

            infectionLevel.ifPresent(il -> {
                var level = il.getLevel();
                il.setLevel(++level);
            });

            deductCube(state, card);
        }
    }

    private static void deductCube(GameState state, Card card) {
        var diseaseCube = state.getDiseaseCubes().stream()
                .filter(c -> c.getColor() == card.getColor()).findFirst();

        diseaseCube.ifPresent(c -> c.setCount(c.getCount() - 1));
    }

    public static void applyInfectionLevels(GameState state, Map<Label, Label> cities) {
        for (Map.Entry<Label, Label> entry : cities.entrySet()) {
            var infectionLevel = state.getInfectionLevels().stream()
                    .filter(c -> c.getCity().equals(entry.getKey().getText())).findFirst();

            infectionLevel.ifPresent(il -> {
                entry.getValue().setText(String.valueOf(il.getLevel()));
            });
        }
    }

    public static boolean applyOutbreakLoseCondition(GameState state) {
        var infectionLevels = state.getInfectionLevels().stream()
                .filter(il -> il.getLevel() >= state.getCubeNumberForOutbreak());
        return infectionLevels.count() >= state.getMaxOutbreaks();
    }

    public static boolean hasUnusedEventCards(GameState state) {
        var eventCards = state.getPlayerState().getHand().stream()
                .filter(c -> c.getType() == CardType.EVENT).toList();

        return !eventCards.isEmpty();
    }
}
