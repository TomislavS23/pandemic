package dev.pandemic.enumerations;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;

@XmlEnum
public enum Color {
    @XmlEnumValue("red") RED,
    @XmlEnumValue("blue") BLUE
}
