package dev.pandemic.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@XmlRootElement(name = "cards")
@NoArgsConstructor
public class CardListDTO {

    @XmlElement(name = "card")
    @Getter
    private ArrayList<CardDTO> cards;
}