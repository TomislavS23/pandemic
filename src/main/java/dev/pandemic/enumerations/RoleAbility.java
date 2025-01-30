package dev.pandemic.enumerations;

import jakarta.xml.bind.annotation.XmlEnumValue;

public enum RoleAbility {
    @XmlEnumValue("treat-disease") TREAT_DISEASE,
    @XmlEnumValue("cure-disease") CURE_DISEASE
}
