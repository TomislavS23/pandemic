package dev.pandemic.enumerations;

import jakarta.xml.bind.annotation.XmlEnum;
import jakarta.xml.bind.annotation.XmlEnumValue;
import lombok.Getter;

@Getter
@XmlEnum
public enum Role {
    @XmlEnumValue("medic") MEDIC("Medic"),
    @XmlEnumValue("scientist") SCIENTIST("Scientist");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
