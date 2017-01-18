package nl.soccar.ui.input;

import javafx.scene.input.KeyCode;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;

import java.util.ArrayList;
import java.util.List;

/**
 * The Keyboard class keeps track of all pressed (and in turn released) keys.
 *
 * @author PTS34A
 */
public final class Keyboard {

    // Stores the key binds.
    private static final List<KeyCode> ACCELERATE;
    private static final List<KeyCode> REVERSE;
    private static final List<KeyCode> BOOST;
    private static final List<KeyCode> STEER_LEFT;
    private static final List<KeyCode> STEER_RIGHT;
    private static final List<KeyCode> HANDBRAKE;
    
    static {
        // Accelerate binds
        ACCELERATE = new ArrayList<>();
        ACCELERATE.add(KeyCode.W);
        ACCELERATE.add(KeyCode.UP);

        // Reverse binds
        REVERSE = new ArrayList<>();
        REVERSE.add(KeyCode.S);
        REVERSE.add(KeyCode.DOWN);

        // Boost binds
        BOOST = new ArrayList<>();
        BOOST.add(KeyCode.SHIFT);
        BOOST.add(KeyCode.CONTROL);

        // Steer left binds
        STEER_LEFT = new ArrayList<>();
        STEER_LEFT.add(KeyCode.A);
        STEER_LEFT.add(KeyCode.LEFT);

        // Steer right binds
        STEER_RIGHT = new ArrayList<>();
        STEER_RIGHT.add(KeyCode.D);
        STEER_RIGHT.add(KeyCode.RIGHT);

        // Handbrake binds
        HANDBRAKE = new ArrayList<>();
        HANDBRAKE.add(KeyCode.SPACE);
    }
    
    // Stores the keys that are being pressed at any time.
    private final List<KeyCode> pressedKeys = new ArrayList<>();

    /**
     * Constructor for initializing the Keyboard object.
     */
    public Keyboard() {
        // Doesnt needs anything for initializing.
    }

    /**
     * Method that adds the given KeyCode to the pressedKeys list.
     *
     * @param code The keycode that needs to be added to the pressedKeys list.
     */
    public void setKeyPressed(KeyCode code) {
        if (!pressedKeys.contains(code)) {
            pressedKeys.add(0, code); // Prepend to the list, so the last key pressed gets first priority
        }
    }

    /**
     * Method that removes the given KeyCode from the pressedKeys list.
     *
     * @param code The keycode that needs to be removed of the pressedKeys list.
     */
    public void setKeyReleased(KeyCode code) {
        pressedKeys.remove(code);
    }

    /**
     * Returns the correct ThrottleAction based on the current pressed keys
     *
     * @return ThrottleAction The current ThrottleAction, not null.
     */
    ThrottleAction getThrottleAction() {
        // The last pressed key
        for (KeyCode pressedKey : pressedKeys) {

            if (BOOST.contains(pressedKey)) {
                return ThrottleAction.BOOST;
            }

            if (ACCELERATE.contains(pressedKey)) {
                return ThrottleAction.ACCELERATE;
            }

            if (REVERSE.contains(pressedKey)) {
                return ThrottleAction.REVERSE;
            }

        }
        return ThrottleAction.IDLE;
    }

    /**
     * Returns the correct SteerAction based on the current pressed keys
     *
     * @return SteerAction The current SteerAction, not null.
     */
    SteerAction getSteerAction() {

        for (KeyCode pressedKey : pressedKeys) {

            if (STEER_LEFT.contains(pressedKey)) {
                return SteerAction.STEER_LEFT;
            }

            if (STEER_RIGHT.contains(pressedKey)) {
                return SteerAction.STEER_RIGHT;
            }

        }
        return SteerAction.NONE;
    }

    /**
     * Returns the correct HandbrakeAction based on the current pressed keys
     *
     * @return HandbrakeAction The current HandbrakeAction, not null.
     */
    HandbrakeAction getHandbrakeAction() {

        for (KeyCode pressedKey : pressedKeys) {

            if (HANDBRAKE.contains(pressedKey)) {
                return HandbrakeAction.ACTIVE;
            }

        }
        return HandbrakeAction.INACTIVE;
    }
}
