package dev.pandemic.game;

import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.City;
import dev.pandemic.enumerations.RoleAbility;
import dev.pandemic.model.Card;
import dev.pandemic.model.State;
import dev.pandemic.model.InfectionLevel;
import jakarta.xml.bind.JAXBException;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Map;

public class GameUtils {
    private GameUtils() {
    }

    public static void applyInfectionCards(State state, List<Card> cards) {
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

    private static void deductCube(State state, Card card) {
        var diseaseCube = state.getDiseaseCubes().stream()
                .filter(c -> c.getColor() == card.getColor()).findFirst();

        diseaseCube.ifPresent(c -> c.setCount(c.getCount() - 1));
    }

    public static void applyInfectionLevels(State state, Map<Label, Label> cities) {
        for (Map.Entry<Label, Label> entry : cities.entrySet()) {
            var infectionLevel = state.getInfectionLevels().stream()
                    .filter(c -> c.getCity().equals(entry.getKey().getText())).findFirst();

            infectionLevel.ifPresent(il -> {
                if (il.isDiseaseCured()) {
                    return;
                }
                entry.getValue().setText(String.valueOf(il.getLevel()));
            });
        }
    }

    public static boolean applyOutbreakLoseCondition(State state) {
        var infectionLevels = state.getInfectionLevels().stream()
                .filter(il -> il.getLevel() >= state.getCubeNumberForOutbreak());
        return infectionLevels.count() >= state.getMaxOutbreaks();
    }

    public static boolean hasUnusedEventCards(State state) {
        var eventCards = state.getPlayerState().getHand().stream()
                .filter(c -> c.getType() == CardType.EVENT).toList();

        return !eventCards.isEmpty();
    }

    public static void applyRoleAbility(State state, RoleAbility roleAbility, Map<Label, Label> cities) {
        var pawnLocation = state.getPlayerState().getPawn().getLocation().getCityName();
        var infectionLevel = state.getInfectionLevels().stream().filter(il -> pawnLocation.equals(il.getCity())).findFirst();
        var city = cities.entrySet().stream().filter(c -> pawnLocation.equals(c.getKey().getText())).findFirst();

        switch (roleAbility) {
            case TREAT_DISEASE -> {
                infectionLevel.ifPresent(il -> {
                    var level = il.getLevel();
                    il.setLevel(level - 1);
                    city.ifPresent(c -> c.getValue().setText(String.valueOf(il.getLevel())));
                });
            }
            case CURE_DISEASE -> {
                infectionLevel.ifPresent(il -> {
                    il.setLevel(0);
                    il.setDiseaseCured(true);
                    city.ifPresent(c -> c.getValue().setText(String.valueOf(il.getLevel())));
                });
            }
        }
    }

    public static boolean applyRoleAbilityWinCondition(State state) {
        var curedCities = state.getInfectionLevels().stream().filter(InfectionLevel::isDiseaseCured).count();
        return curedCities >= 3;
    }

    public static boolean applyNoDiseaseCubesLeftWinCondition(State state) {
        var infectionLevelCount = state.getInfectionLevels().stream()
                .filter(il -> il.getLevel() >= state.getCubeNumberForOutbreak()).count();
        var diseaseCubeCountRed = state.getDiseaseCubes().get(0).getCount();
        var diseaseCubeCountBlue = state.getDiseaseCubes().get(1).getCount();

        return (infectionLevelCount < state.getMaxOutbreaks()) && (diseaseCubeCountRed == 0) && (diseaseCubeCountBlue == 0);
    }

    public static void applyPlayerCard(State state, Card card, Label lbCurrentPawnLocationP1) throws JAXBException {
        var cardType = card.getType();
        var eventType = card.getEventType();

        switch (cardType) {
            case CITY -> {
                var city = City.fromName(card.getName());
                state.getPlayerState().getPawn().setLocation(city);
                lbCurrentPawnLocationP1.setText(city.getCityName());
            }
            case EVENT -> {
                switch (eventType) {
                    case INCREASE_INFECTION_RATE -> {
                        var infectionRate = state.getInfectionRateCounter();
                        state.setInfectionRateCounter(infectionRate + 1);
                    }
                    case DRAW_INFECTION_CARDS -> {
                        CardUtils.fillAndShuffleDeck(state, CardType.INFECTION);
                        applyInfectionCards(state, CardUtils.drawInfectionCards(state));
                    }
                }
            }
        }
    }
}
