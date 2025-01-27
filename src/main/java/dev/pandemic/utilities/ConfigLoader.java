package dev.pandemic.utilities;

import dev.pandemic.enumerations.Color;
import dev.pandemic.model.DiseaseCube;
import dev.pandemic.model.GameState;

import java.util.ArrayList;

public class ConfigLoader {

    private ConfigLoader() {
    }

    public static GameState prepareGameConfig() {
        var cubes = new ArrayList<DiseaseCube>();
        cubes.add(new DiseaseCube(Color.RED));
        cubes.add(new DiseaseCube(Color.BLUE));

        return new GameState(cubes);
    }
}
