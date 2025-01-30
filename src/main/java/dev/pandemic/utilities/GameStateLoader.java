package dev.pandemic.utilities;

import dev.pandemic.dto.CardListDTO;
import dev.pandemic.enumerations.Color;
import dev.pandemic.enumerations.FilePath;
import dev.pandemic.model.DiseaseCube;
import dev.pandemic.model.GameState;
import dev.pandemic.model.Player;
import jakarta.xml.bind.JAXBException;

import java.util.ArrayList;

public class GameStateLoader {

    private GameStateLoader() {
    }

    public static GameState prepareGameState() throws JAXBException {
        var cubes = getDiseaseCubes();
        var cards = getCards();
        var player = new Player();

        return new GameState(cubes, player);
    }

    public static CardListDTO getCards() throws JAXBException {
        return (CardListDTO) JAXBUtils.load(CardListDTO.class, FilePath.CARDS_CONFIG.getPath());
    }

    private static ArrayList<DiseaseCube> getDiseaseCubes() {
        var cubes = new ArrayList<DiseaseCube>();
        cubes.add(new DiseaseCube(Color.RED));
        cubes.add(new DiseaseCube(Color.BLUE));
        return cubes;
    }
}
