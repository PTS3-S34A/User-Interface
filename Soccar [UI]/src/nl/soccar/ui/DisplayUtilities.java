package nl.soccar.ui;

import javafx.scene.control.Alert;

/**
 * Utility class that contains a static method for displaying alerts on the user
 * interface.
 *
 * @author PTS34A
 */
public final class DisplayUtilities {

    private DisplayUtilities() {
        /**
         * Constructor is intentionally set private, so that this class can
         * never be initialized.
         */
    }

    /**
     * Displays an alter message with the provided tiel and message on the user
     * interface.
     *
     * @param title The title of the alert.
     * @param message The message of the alert.
     */
    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
