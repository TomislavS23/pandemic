package dev.pandemic.dto;

import dev.pandemic.enumerations.*;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class CardDTO {
    @XmlElement(nillable = true, name = "title")
    public String name;
    @XmlElement(nillable = true, name = "description")
    public String description;
    @XmlAttribute(name = "color")
    public Color color;
    @XmlAttribute(name = "card-type")
    public CardType type;
    @XmlAttribute(name = "role")
    public Role role;
    @XmlAttribute(name = "event-type")
    public CardEventType eventType;
}
