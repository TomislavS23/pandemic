package dev.pandemic.model;

import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlTransient;
import jakarta.xml.bind.annotation.XmlType;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Setter
@AllArgsConstructor
@XmlType
public class InfectionLevel implements Serializable {
    @XmlAttribute
    private String  city;
    @XmlAttribute
    private int level;
    @XmlAttribute
    private boolean diseaseCured;

    @XmlTransient
    public String getCity() {
        return city;
    }

    @XmlTransient
    public int getLevel() {
        return level;
    }

    @XmlTransient
    public boolean isDiseaseCured() {
        return diseaseCured;
    }
}
