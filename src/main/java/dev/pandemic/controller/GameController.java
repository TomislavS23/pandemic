package dev.pandemic.controller;

import dev.pandemic.PandemicApplication;
import dev.pandemic.enumerations.CardType;
import dev.pandemic.enumerations.PlayerType;
import dev.pandemic.game.CardUtils;
import dev.pandemic.game.GameUtils;
import dev.pandemic.model.GameState;
import dev.pandemic.model.State;
import dev.pandemic.networking.Networking;
import dev.pandemic.fxutilities.AlertUtils;
import dev.pandemic.game.GameStateLoader;
import dev.pandemic.fxutilities.FXUtils;
import dev.pandemic.fxutilities.SceneLoader;
import dev.pandemic.networking.rmi.ChatRemoteService;
import dev.pandemic.networking.rmi.RMIServer;
import jakarta.xml.bind.JAXBException;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import lombok.Getter;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameController {
    @Getter
    private static GameController instance;
    private static final String PAUSE_MENU_VIEW_PATH = "/views/pause-menu-view.fxml";
    private static Boolean EVENTS_REGISTERED = false;
    private State state;
    private static boolean isInfectionCardDrawn = false;
    private static final Map<Label, Label> fields = new HashMap<>();
    private static final ArrayList<Button> buttons = new ArrayList<>();
    private static final int P01_PORT = 1990;
    private static final int P02_PORT = 1989;
    public static ChatRemoteService chatRemoteService;


    public GameController() {
        instance = this;
    }

    @FXML
    public SplitPane splitPaneMain;
    @FXML
    public Label lbInfectionRate;
    @FXML
    public Label lbActionsLeftPlayer1;
    @FXML
    public Label lbCurrentPawnLocationP1;
    @FXML
    public Button btnUseCard;
    @FXML
    public Label lbPlayer1Role;
    @FXML
    public Button btnEndTurn;
    @FXML
    public Button btnDrawCard;
    @FXML
    public ListView lvCardsPlayer1;
    @FXML
    public ListView lvCardsPlayer2;
    @FXML
    public Button btnDrawInfectionCards;
    @FXML
    public Label lbLA;
    @FXML
    public Label lbMC;
    @FXML
    public Label lbBogota;
    @FXML
    public Label lbBuenosAries;
    @FXML
    public Label lbLondon;
    @FXML
    public Label lbStPetersburg;
    @FXML
    public Label lbIstanbul;
    @FXML
    public Label lbShanghai;
    @FXML
    public Label lbInfectionLevelLA;
    @FXML
    public Label lbInfectionLevelMC;
    @FXML
    public Label lbInfectionLevelBogota;
    @FXML
    public Label lbInfectionLevelBA;
    @FXML
    public Label lbInfectionLevelLondon;
    @FXML
    public Label lbInfectionLevelSP;
    @FXML
    public Label lbInfectionLevelIstanbul;
    @FXML
    public Label lbInfectionLevelShanghai;
    @FXML
    public Button btnUseRoleAbility;
    @FXML
    public Label lbPlayer2Role;
    @FXML
    public Label lbActionsLeftPlayer2;
    @FXML
    public Label lbCurrentPawnLocationP2;
    @FXML
    public TextArea taMessages;
    @FXML
    public TextField tfChatMessage;
    @FXML
    public Button btnSendMessage;

    @FXML
    public void initialize() {
        if (!PlayerType.SINGLEPLAYER.name().equals(PandemicApplication.playerType.name())) {
            initializeRegistry();
        }
        initializeGameState();
        initializeEvents();
        initializeFields();
        FXUtils.setFieldValues(state, fields);
        initializeButtons();
        initializeLists();
    }

    private void initializeRegistry() {
        try {
            Registry registry = LocateRegistry.getRegistry(RMIServer.HOSTNAME, RMIServer.RMI_PORT);
            chatRemoteService = (ChatRemoteService) registry.lookup(ChatRemoteService.REMOTE_OBJECT_NAME);
        } catch (NotBoundException | RemoteException e) {
            e.printStackTrace();
        }

        Timeline refreshTimeLine = getChatRefreshTimeline();
        refreshTimeLine.play();
    }

    public Timeline getChatRefreshTimeline() {
        Timeline refreshTimeLine = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            try {
                List<String> chatMessages = chatRemoteService.getAllMessages();
                StringBuilder sb = new StringBuilder();

                chatMessages.forEach(m -> {
                    sb.append(m).append("\n");
                });

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

    private void initializeButtons() {
        if (buttons.isEmpty()) {
            buttons.add(btnDrawInfectionCards);
            buttons.add(btnDrawCard);
            buttons.add(btnUseRoleAbility);
            buttons.add(btnUseCard);
            buttons.add(btnEndTurn);
            return;
        }
        FXUtils.disableButtons(false, buttons);
    }

    private void initializeLists() {
        state.getPlayer01State().postLoad();
        state.getPlayer02State().postLoad();
        lvCardsPlayer1.setItems(state.getPlayer01State().getHand());
        lvCardsPlayer2.setItems(state.getPlayer02State().getHand());
    }

    private void initializeFields() {
        initCityFields();
        initStatusFields();
    }

    private void initStatusFields() {
        var player01State = state.getPlayer01State();
        var player02State = state.getPlayer02State();

        lbInfectionRate.setText(String.valueOf(state.getInfectionRateCounter()));
        lbActionsLeftPlayer1.setText(String.valueOf(player01State.getActionsLeft()));
        lbActionsLeftPlayer2.setText(String.valueOf(player02State.getActionsLeft()));
        lbCurrentPawnLocationP1.setText(player01State.getPawn().getLocation().getCityName());
        lbCurrentPawnLocationP2.setText(player02State.getPawn().getLocation().getCityName());
        lbPlayer1Role.setText(player01State.getRole().getName());
        lbPlayer2Role.setText(player02State.getRole().getName());
    }

    private void initCityFields() {
        fields.put(lbLA, lbInfectionLevelLA);
        fields.put(lbMC, lbInfectionLevelMC);
        fields.put(lbBogota, lbInfectionLevelBogota);
        fields.put(lbBuenosAries, lbInfectionLevelBA);
        fields.put(lbLondon, lbInfectionLevelLondon);
        fields.put(lbStPetersburg, lbInfectionLevelSP);
        fields.put(lbIstanbul, lbInfectionLevelIstanbul);
        fields.put(lbShanghai, lbInfectionLevelShanghai);
    }

    private void initializeGameState() {
        try {
            if (GameState.getInstance().getState() != null) {
                state = GameState.getInstance().getState();
                return;
            }

            GameState.getInstance().setState(GameStateLoader.prepareGameState());
            state = GameState.getInstance().getState();
        } catch (JAXBException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void initializeEvents() {
        Platform.runLater(this::registerEvents);
    }

    private void registerEvents() {
        if (!EVENTS_REGISTERED) {
            splitPaneMain.getScene().addEventHandler(KeyEvent.KEY_RELEASED, this::keyReleasedEvent);
            btnDrawInfectionCards.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawInfectionCards);
            btnDrawCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::drawPlayerCard);
            btnUseCard.addEventHandler(MouseEvent.MOUSE_CLICKED, this::useCard);
            btnEndTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, this::endTurn);
            btnUseRoleAbility.addEventHandler(MouseEvent.MOUSE_CLICKED, this::useAbility);
            btnSendMessage.setOnAction(this::sendChatMessage);
            EVENTS_REGISTERED = true;
        }
    }

    private void useAbility(MouseEvent mouseEvent) {
        if (PlayerType.PLAYER_02.name().equals(PandemicApplication.playerType.name())) {
            GameUtils.applyRoleAbility(state, state.getPlayer02State().getAbility(), fields);
            btnUseRoleAbility.setDisable(true);

            AlertUtils.showAlert(
                    "Infection level decreased.",
                    "You have decreased infection level in this city by 1 point.",
                    Alert.AlertType.INFORMATION
            );
            Networking.synchronise(P01_PORT);
        } else if (PlayerType.PLAYER_01.name().equals(PandemicApplication.playerType.name())) {
            GameUtils.applyRoleAbility(state, state.getPlayer01State().getAbility(), fields);
            btnUseRoleAbility.setDisable(true);

            AlertUtils.showAlert("City healed.", "City has been cured successfully.", Alert.AlertType.INFORMATION);
            Networking.synchronise(P02_PORT);
        }

        GameUtils.applyRoleAbility(state, state.getPlayer01State().getAbility(), fields);
        btnUseRoleAbility.setDisable(true);

        if (GameUtils.applyRoleAbilityWinCondition(state))
            AlertUtils.showAlert("Game Won", "You successfully cured 3 or more cities.", Alert.AlertType.INFORMATION);
    }

    @FXML
    private void endTurn(MouseEvent mouseEvent) {
        if (!isInfectionCardDrawn) {
            AlertUtils.showAlert("Error", "You have to draw infection cards before finishing turn.", Alert.AlertType.WARNING);
            return;
        }

        if (GameUtils.hasUnusedEventCards(state)) {
            AlertUtils.showAlert("Error", "Cannot finish this turn! You have unused event cards.", Alert.AlertType.WARNING);
            return;
        }

        var player01State = state.getPlayer01State();
        var player02State = state.getPlayer02State();
        isInfectionCardDrawn = false;
        btnUseRoleAbility.setDisable(false);
        player01State.setActionsLeft(4);
        player02State.setActionsLeft(4);
        btnDrawInfectionCards.setDisable(isInfectionCardDrawn);
        lbActionsLeftPlayer1.setText(String.valueOf(player01State.getActionsLeft()));
        lbActionsLeftPlayer1.setText(String.valueOf(player02State.getActionsLeft()));
        player01State.preSave();
        player02State.preSave();

        if (PlayerType.PLAYER_02.name().equals(PandemicApplication.playerType.name())) {
            FXUtils.disableButtons(true, buttons);
            Networking.synchronise(P01_PORT);
        } else if (PlayerType.PLAYER_01.name().equals(PandemicApplication.playerType.name())) {
            FXUtils.disableButtons(true, buttons);
            Networking.synchronise(P02_PORT);
        }
    }

    @FXML
    private void drawPlayerCard(MouseEvent mouseEvent) {
        try {
            if (PlayerType.PLAYER_02.name().equals(PandemicApplication.playerType.name())) {
                FXUtils.handleDrawPlayerCard(state, lbActionsLeftPlayer2, PlayerType.PLAYER_02);
            } else if (PlayerType.PLAYER_01.name().equals(PandemicApplication.playerType.name())) {
                FXUtils.handleDrawPlayerCard(state, lbActionsLeftPlayer1, PlayerType.PLAYER_01);
            } else {
                FXUtils.handleDrawPlayerCard(state, lbActionsLeftPlayer1, PlayerType.SINGLEPLAYER);
            }
        } catch (JAXBException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void drawInfectionCards(MouseEvent mouseEvent) {
        try {
            if (state.getInfectionCards().isEmpty() || state.getInfectionRateCounter() > (long) state.getInfectionCards().size()) {
                CardUtils.fillAndShuffleDeck(state, CardType.INFECTION);
            }

            GameUtils.applyInfectionCards(state, CardUtils.drawInfectionCards(state));
            GameUtils.applyInfectionLevels(state, fields);

            isInfectionCardDrawn = true;
            btnDrawInfectionCards.setDisable(isInfectionCardDrawn);

            if (GameUtils.applyOutbreakLoseCondition(state))
                AlertUtils.showAlert("Game Lost", "You have lost the game.", Alert.AlertType.INFORMATION);

            if (GameUtils.applyNoDiseaseCubesLeftWinCondition(state))
                AlertUtils.showAlert("Game Won!", "You have won the game.", Alert.AlertType.INFORMATION);

            if (PlayerType.PLAYER_02.name().equals(PandemicApplication.playerType.name())) {
                Networking.synchronise(P01_PORT);
            } else if (PlayerType.PLAYER_01.name().equals(PandemicApplication.playerType.name())) {
                Networking.synchronise(P02_PORT);
                Platform.runLater(Networking::sendRequest);
            }
        } catch (Exception e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void useCard(MouseEvent mouseEvent) {
        try {
            if (PlayerType.PLAYER_02.name().equals(PandemicApplication.playerType.name())) {
                FXUtils.handleCardSelection(state, lvCardsPlayer2, PlayerType.PLAYER_02, lbCurrentPawnLocationP2, lbInfectionRate);
            } else if (PlayerType.PLAYER_01.name().equals(PandemicApplication.playerType.name())) {
                FXUtils.handleCardSelection(state, lvCardsPlayer1, PlayerType.PLAYER_01, lbCurrentPawnLocationP1, lbInfectionRate);
            } else {
                FXUtils.handleCardSelection(state, lvCardsPlayer1, lbCurrentPawnLocationP1, lbInfectionRate);
            }
        } catch (JAXBException e) {
            AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void keyReleasedEvent(KeyEvent keyEvent) {
        if (keyEvent.getCode() == KeyCode.ESCAPE) {
            try {
                SceneLoader.loadScene(
                        PAUSE_MENU_VIEW_PATH,
                        (Stage) splitPaneMain.getScene().getWindow(),
                        "Pause Menu",
                        false
                );
            } catch (IOException e) {
                AlertUtils.showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void sendChatMessage(ActionEvent actionEvent) {
        try {
            String chatMessage = tfChatMessage.getText();
            chatRemoteService.sendChatMessage(PandemicApplication.playerType + ": " + chatMessage);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
