package dev.pandemic.thread;

import dev.pandemic.enumerations.FilePath;
import dev.pandemic.model.State;
import dev.pandemic.utilities.serialization.Serialization;
import javafx.application.Platform;
import javafx.scene.control.Label;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.util.Optional;
import java.util.Stack;

@Data
@RequiredArgsConstructor
public class ReadLastState implements Runnable {

    private final Label label;
    private static final Serialization serialization;

    static {
        serialization = new Serialization();
    }

    @Override
    public void run() {
        try {
            Optional<Object> obj = serialization.deserializeObject(FilePath.REPLAY_PATH.getPath());

            obj.ifPresent(object -> {
                Stack<State> states = (Stack<State>) object;
                Platform.runLater(() -> label.setText("Last card used:" + states.peek().getCardDiscardPile().getLast()));
            });
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
