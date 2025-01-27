package dev.pandemic.model;

import dev.pandemic.enumerations.Role;
import dev.pandemic.enumerations.RoleAbility;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Player {
    private final String id;
    private Role role;
    private RoleAbility ability;
    private int actionsLeft;
    private List<Card> hand;
    private final int maxHandSize;
}
