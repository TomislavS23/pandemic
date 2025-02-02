package dev.pandemic.model;

import dev.pandemic.enumerations.Color;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@XmlType
public class DiseaseCube {
    @XmlAttribute
    private Color color;
    @XmlValue
    private int count;

    public DiseaseCube(Color color, int count) {
        this.color = color;
        this.count = count;
    }

    @XmlTransient
    public Color getColor() {
        return color;
    }

    @XmlTransient
    public int getCount() {
        return count;
    }
}
