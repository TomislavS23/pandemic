package dev.pandemic.enumerations;

import lombok.Getter;

@Getter
public enum City {
    MEXICO_CITY("Mexico City"),
    LOS_ANGELES("Los Angeles"),
    BOGOTA("Bogotá"),
    BUENOS_AIRES("Buenos Aires"),
    ST_PETERSBURG("St. Petersburg"),
    ISTANBUL("Istanbul"),
    ALGIERS("Algiers"),
    LONDON("London");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }

}

