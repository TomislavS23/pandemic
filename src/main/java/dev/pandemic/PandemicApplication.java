package dev.pandemic;

import dev.pandemic.enumerations.PlayerType;
import dev.pandemic.game.GameStateLoader;
import dev.pandemic.game.StartGameUtils;
import dev.pandemic.model.GameState;
import dev.pandemic.networking.Networking;
import dev.pandemic.fxutilities.MessageUtils;
import jakarta.xml.bind.JAXBException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class PandemicApplication extends Application {
    public static PlayerType playerType;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PandemicApplication.class.getResource("/views/menu-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1920, 1080);
        stage.setTitle("Pandemic Board Game" + " - " + playerType.toString());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        startServer();
    }

    private static void startServer() {
        try {
            if (!PlayerType.SINGLEPLAYER.name().equals(playerType.name()) &&
                    PlayerType.PLAYER_02.name().equals(playerType.name())) {
                GameState.getInstance().setState(GameStateLoader.prepareGameState());
                new Thread(() -> {
                    Networking.PORT = 1989;
                    Networking.acceptRequests();
                }).start();
            } else {
                new Thread(() -> {
                    Networking.PORT = 1990;
                    Networking.acceptRequests();
                }).start();
            }
        } catch (JAXBException e) {
            MessageUtils.showMessage(e.getMessage(), "Error", 1);
        }
    }

    public static void main(String[] args) {
        if (!StartGameUtils.playerExists(args[0])) {
            MessageUtils.showMessage("Wrong user.", "Error", 1);
        } else {
            playerType = PlayerType.valueOf(args[0]);
            launch();
        }
    }
}