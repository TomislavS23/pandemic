package dev.pandemic.fxutilities;

import dev.pandemic.annotations.UseJavaFXDialog;

import javax.swing.*;

public class MessageUtils {
    private MessageUtils() {
    }

    /**
     * Displays JOptionPane dialog.
     * It is recommended to use methods that implement JavaFX alerts instead of using JOptionPane.
     * Use this method only if needed outside of JavaFX scope
     *
     * @param message message that will be shown in the body
     * @param title title shown at top of the dialog
     * @param messageType specifies type of the dialog
     * @see AlertUtils
     */
    @UseJavaFXDialog
    @Deprecated
    public static void showMessage(String message, String title, Integer messageType) {
        JOptionPane.showMessageDialog(null, message, title, messageType);
    }
}
