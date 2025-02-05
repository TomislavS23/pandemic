package dev.pandemic.model;

import dev.pandemic.enumerations.Role;
import dev.pandemic.enumerations.RoleAbility;
import jakarta.xml.bind.annotation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Player implements Serializable {
    @XmlAttribute
    private String username = "PLAYER_01";
    @XmlAttribute
    private Role role;
    @XmlAttribute
    private RoleAbility ability;
    @XmlAttribute(name = "actions-left")
    private int actionsLeft;
    @XmlElement
    private Pawn pawn;
    @XmlElementWrapper(name = "hand")
    @XmlElement(name = "card")
    private List<Card> handList;
    private transient ObservableList<Card> hand;

    public void postLoad() {
        if (handList != null) {
            hand = FXCollections.observableArrayList(handList);
        }
    }

    public void preSave() {
        if (hand != null) {
            handList = hand.stream().toList();
        }
    }

    public Player(Role role, RoleAbility ability) {
        this.role = role;
        this.ability = ability;
    }

    @XmlTransient
    public String getUsername() {
        return username;
    }

    @XmlTransient
    public Role getRole() {
        return role;
    }

    @XmlTransient
    public RoleAbility getAbility() {
        return ability;
    }

    @XmlTransient
    public int getActionsLeft() {
        return actionsLeft;
    }

    @XmlTransient
    public Pawn getPawn() {
        return pawn;
    }

    @XmlTransient
    public ObservableList<Card> getHand() {
        return hand;
    }
}
