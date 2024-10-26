package dev.pandemic.service.utilities;

import dev.pandemic.service.annotations.UseJavaFXDialog;

import javax.swing.*;

public class MessageUtils {
    private MessageUtils() {
    }

    /**
     * Displays JOptionPane dialog.
     * It is recommended to use methods that implement JavaFX alerts instead of using JOptionPane.
     *
     * @param message
     * @param title
     * @param messageType
     * @see AlertUtils
     */
    @UseJavaFXDialog
    public static void showMessage(String message, String title, Integer messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
