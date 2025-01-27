package dev.pandemic.model;

import dev.pandemic.enumerations.Color;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@XmlType
public class DiseaseCube {
    @XmlElement
    private Color color;
    @XmlElement
    private int count = 24;

    public DiseaseCube(Color color) {
        this.color = color;
    }
}
