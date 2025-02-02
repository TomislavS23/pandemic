package dev.pandemic.model;

import dev.pandemic.enumerations.Role;
import dev.pandemic.enumerations.RoleAbility;
import jakarta.xml.bind.annotation.*;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {
    @XmlAttribute
    private String username = "PLAYER_01";
    @XmlAttribute
    private Role role;
    @XmlAttribute
    private RoleAbility ability;
    @XmlAttribute(name = "actions-left")
    private int actionsLeft = 4;
    @XmlElement
    private Pawn pawn;
    @XmlElementWrapper(name = "cards")
    @XmlElement(name = "hand", nillable = true)
    private ObservableList<Card> hand;

    public Player(Role role, RoleAbility ability) {
        this.role = role;
        this.ability = ability;
    }
}
