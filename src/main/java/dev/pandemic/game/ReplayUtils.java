package dev.pandemic.game;

import dev.pandemic.model.State;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Stack;

public class ReplayUtils {
    private static ReplayUtils instance;
    @Getter
    @Setter
    private Stack<State> states = new Stack<>();

    private ReplayUtils() {
    }

    public static ReplayUtils getInstance() {
        if (instance == null) {
            instance = new ReplayUtils();
        }
        return instance;
    }

    public void appendToReplay(State state) {
        states.add(state);
    }

    public State readLastMove() {
        return states.getLast();
    }
}
