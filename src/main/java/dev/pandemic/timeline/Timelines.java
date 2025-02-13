package dev.pandemic.timeline;

import dev.pandemic.networking.rmi.ChatService;
import dev.pandemic.thread.ReadLastState;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.util.Duration;

import java.rmi.RemoteException;
import java.util.List;

public class Timelines {
    private Timelines() {

    }

    public static Timeline getChatRefreshTimeline(ChatService chatService, TextArea taMessages) {
        Timeline refreshTimeLine = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                List<String> chatMessages = chatService.getAllMessages();
                StringBuilder sb = new StringBuilder();

                chatMessages.forEach(m -> sb.append(m).append("\n"));

                taMessages.setText(sb.toString());
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }
        }),
                new KeyFrame(Duration.seconds(1))
        );
        refreshTimeLine.setCycleCount(Animation.INDEFINITE);
        return refreshTimeLine;
    }

    public static Timeline getLastMoveRefreshTimeline(Label label) {
        Timeline refreshTimeLine = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            ReadLastState rls = new ReadLastState(label);
            new Thread(rls, "read-state-thread").start();
        }),
                new KeyFrame(Duration.seconds(5))
        );
        refreshTimeLine.setCycleCount(Animation.INDEFINITE);
        return refreshTimeLine;
    }
}
