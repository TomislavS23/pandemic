package dev.pandemic.model;

import dev.pandemic.enumerations.Role;
import dev.pandemic.enumerations.RoleAbility;
import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.annotation.ElementType;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
public class Player {
    @XmlAttribute
    public String username = "PLAYER_01";
    @XmlAttribute
    private Role role = Role.MEDIC;
    @XmlAttribute
    private RoleAbility ability = RoleAbility.TREAT_DISEASE;
    private int actionsLeft = 4;
    @XmlElementWrapper(name = "cards")
    @XmlElement(name = "hand", nillable = true)
    private List<Card> hand;
}
