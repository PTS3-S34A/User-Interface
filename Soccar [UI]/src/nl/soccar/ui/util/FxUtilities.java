package nl.soccar.ui.util;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * @author PTS34A
 */
public final class FxUtilities {

    private FxUtilities() {
        // Utility class should not be instantiated.
    }

    /**
     * Shows a message next to a Node.
     *
     * @param node    A Node used to calculate the message's position. Should not be null.
     * @param message The message to actually show. Should not be null or empty.
     */
    public static void showInlineMessage(Node node, String message) {
        ContextMenu menu = new ContextMenu();
        menu.setAutoHide(false);

        MenuItem item = new MenuItem(message);

        menu.getItems().add(item);
        menu.show(node, Side.RIGHT, 10, 0);

        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (newValue) {
                    menu.hide();
                    node.focusedProperty().removeListener(this);
                }
            }
        });
    }

    /**
     * Shows a popup message with a specific title, message and type.
     *
     * @param title   The title that should be displayed in the Alert. Should not be null or empty.
     * @param message The message that should be displayed in the Alert. Should not be null or empty.
     * @param type    The type of the Alert (used to show an image). Should not be null.
     */
    public static void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);

        alert.showAndWait();
    }

}
