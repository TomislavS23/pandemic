package dev.pandemic.fxutilities;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertUtils {
    private AlertUtils() {
    }

    /**
     * Shows specified alert type without header.
     *
     * @param title title that will be shown at the top of an alert
     * @param content content that will be present inside the body
     * @param alertType enum that specifies type of the alert
     */
    public static void showAlert(String title, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setTitle(title);
        alert.setContentText(content);

        alert.showAndWait();
    }


    /**
     * Shows specified alert type with header.
     *
     * @param title title that will be shown at the top of an alert
     * @param header specifies a text that will be shown in alert header
     * @param content content that will be present inside the body
     * @param alertType enum that specifies type of the alert
     */
    public static void showAlert(String title, String header, String content, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }


    /**
     * Shows a confirm dialog without special headers or graphics and returns a value.
     *
     * @param title title that will be shown at the top of a confirmation dialog
     * @param content content that will be present inside the body
     * @return Boolean
     */
    public static Boolean showConfirmationAlert(String title, String content) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText(null);
        alert.setGraphic(null);
        alert.setTitle(title);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();

        return result.get() == ButtonType.OK;
    }
}
