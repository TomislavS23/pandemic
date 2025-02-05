package dev.pandemic.model;

import dev.pandemic.enumerations.City;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlType
public class Pawn implements Serializable {
    @XmlElement(name = "pawn-location")
    private City location;

    @XmlTransient
    public City getLocation() {
        return location;
    }
}
