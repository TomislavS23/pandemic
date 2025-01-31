package dev.pandemic.model;

import dev.pandemic.enumerations.CardEventType;
import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.Color;
import dev.pandemic.enumerations.Role;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
    private String name;
    private String description;
    private Color color;
    private CardType type;
    private Role role;
    private CardEventType eventType;
}
