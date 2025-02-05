package dev.pandemic.game;

import dev.pandemic.enumerations.PlayerType;

public class StartGameUtils {
    private StartGameUtils(){

    }

    public static Boolean playerExists(String player){
        for (PlayerType playerType : PlayerType.values()) {
            if (player.equals(playerType.toString())) {
                return true;
            }
        }

        return false;
    }
}
