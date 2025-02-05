package dev.pandemic.model;

import dev.pandemic.enumerations.Color;
import dev.pandemic.utilities.SerializationUtils;
import jakarta.xml.bind.annotation.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@XmlType
public class DiseaseCube implements Serializable {
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
