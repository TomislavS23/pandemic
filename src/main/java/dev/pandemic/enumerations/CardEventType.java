package dev.pandemic.enumerations;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum CardEventType {
    @XmlEnumValue("increase-infection-rate") INCREASE_INFECTION_RATE,
    @XmlEnumValue("draw-infection-cards") DRAW_INFECTION_CARDS,
    @XmlEnumValue("shuffle-infection-cards") SHUFFLE_INFECTION_CARDS
}
