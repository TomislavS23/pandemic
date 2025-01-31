package dev.pandemic.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@AllArgsConstructor
@XmlType
public class InfectionLevel {
    @XmlAttribute
    private String  city;
    @XmlAttribute
    private int level;

    @XmlTransient
    public String getCity() {
        return city;
    }

    @XmlTransient
    public int getLevel() {
        return level;
    }
}
