package dev.pandemic.model;

import dev.pandemic.enumerations.Color;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@XmlType
public class DiseaseCube {
    @XmlAttribute
    private Color color;
    @XmlValue
    private int count = 24;

    public DiseaseCube(Color color) {
        this.color = color;
    }
}
