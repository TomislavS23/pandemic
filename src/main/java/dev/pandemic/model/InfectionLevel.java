package dev.pandemic.model;

import dev.pandemic.enumerations.City;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@XmlType
public class InfectionLevel {
    @XmlAttribute
    private String  city;
    @XmlAttribute
    private int level;
}
