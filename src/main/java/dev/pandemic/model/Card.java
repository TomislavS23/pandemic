package dev.pandemic.model;

import dev.pandemic.enumerations.CardEventType;
import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.Color;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Card {
    @XmlElement(nillable = true)
    public String name;
    @XmlElement(nillable = true)
    public String description;
    @XmlElement(nillable = true)
    public Color color;
    @XmlElement(nillable = true)
    public CardType type;
    @XmlElement(nillable = true)
    public CardEventType eventType;
}
