package dev.pandemic.thread;

import dev.pandemic.enumerations.FilePath;
import dev.pandemic.game.ReplayUtils;
import dev.pandemic.model.State;
import dev.pandemic.utilities.serialization.Serialization;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SaveLastState implements Runnable {
    private final State state;

    private static final Serialization serialization;

    static {
        serialization = new Serialization();
    }

    @Override
    public void run() {
        ReplayUtils.getInstance().appendToReplay(state);
        serialization.serializeObject(ReplayUtils.getInstance().getStates(), FilePath.REPLAY_PATH.getPath());
    }
}
