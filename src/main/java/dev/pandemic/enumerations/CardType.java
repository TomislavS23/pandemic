package dev.pandemic.enumerations;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum CardType {
    @XmlEnumValue("city-card") CITY,
    @XmlEnumValue("infection-card") INFECTION,
    @XmlEnumValue("event") EVENT,
    @XmlEnumValue("role") ROLE
}
