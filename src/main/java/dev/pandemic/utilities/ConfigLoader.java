package dev.pandemic.utilities;

import dev.pandemic.PandemicApplication;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.ArrayList;

public class ConfigLoader {
    private final String CARDS_CONFIG = "/game/cards/cards.yml";

    private final Yaml yaml;

    public ConfigLoader() {
        this.yaml = new Yaml();
    }

    public Object loadCardsConfig() {
        InputStream inputStream = PandemicApplication.class.getResourceAsStream(CARDS_CONFIG);
        return yaml.load(inputStream);
    }
}
