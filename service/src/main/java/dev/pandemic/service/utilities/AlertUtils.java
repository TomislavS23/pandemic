package dev.pandemic.service.utilities;

import javafx.scene.control.Alert;

import java.util.Optional;

public class AlertUtils {
    private AlertUtils() {
    }

    public static void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);

        alert.showAndWait();
    }

    public static void showAlert(String title, String header, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
}
