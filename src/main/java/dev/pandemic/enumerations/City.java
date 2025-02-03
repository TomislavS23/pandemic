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
    SHANGHAI("Shanghai"),
    LONDON("London");

    private final String cityName;

    City(String cityName) {
        this.cityName = cityName;
    }

    public static City fromName(String name) {
        for (City city : City.values()) {
            if (city.cityName.equalsIgnoreCase(name)) {
                return city;
            }
        }
        throw new IllegalArgumentException("No enum constant for city name: " + name);
    }
}

