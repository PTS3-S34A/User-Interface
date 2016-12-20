package nl.soccar.ui.input;

import java.util.Timer;
import java.util.TimerTask;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;

/**
 *
 * @author PTS34A
 */
public final class GamePad {

    private static final int ACTION_STEER_LEFT = 40;
    private static final int ACRION_STEER_RIGHT = 60;
    private static final int ACTION_THROTTLE = 45;
    private static final int ACTION_REVERSE = 55;
    private static final int POLL_TIME = 50;

    private Controller controller;

    private ThrottleAction throttleAction;
    private HandbrakeAction handbrakeAction;
    private SteerAction steerAction;

    private boolean isPolling = false;
    private Timer timer = new Timer(true);
    private int xAxisPercentage;

    public GamePad() {
        throttleAction = ThrottleAction.IDLE;
        handbrakeAction = HandbrakeAction.INACTIVE;
        steerAction = SteerAction.NONE;
    }

    public GamePad(Controller controller) {
        this.controller = controller;
        isPolling = true;

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                polling(controller);
            }
        }, 0, POLL_TIME);
    }

    private void polling(Controller controller) {
        if (!isPolling) {
            return;
        }
        if (!controller.poll()) {
            isPolling = false;
            return;
        }

        Component[] components = controller.getComponents();
        for (Component c : components) {
            Component.Identifier componentIdentifier = c.getIdentifier();
            processButtonActions(c, componentIdentifier);
            processAnalogActions(c, componentIdentifier);
        }
        processSteerActions();
    }

    private void processAnalogActions(Component c, Component.Identifier componentIdentifier) {
        if (!c.isAnalog()) {
            return;
        }

        float axisValue = c.getPollData();
        int axisValueInPercentage = getAxisValueInPercentage(axisValue);
        // Checks if the component is the X axis.
        if (componentIdentifier == Component.Identifier.Axis.X) {
            xAxisPercentage = axisValueInPercentage;
            return; // Go to next component.
        }
        // Checks if the component is the Y axis.
        if (componentIdentifier == Component.Identifier.Axis.Y) {
            return; // Go to next component
        }

        if (axisValueInPercentage <= ACTION_THROTTLE) {
            throttleAction = ThrottleAction.ACCELERATE;
        } else if (axisValueInPercentage >= ACTION_REVERSE) {
            throttleAction = ThrottleAction.REVERSE;
        } else if (throttleAction != ThrottleAction.BOOST) {
            throttleAction = throttleAction.IDLE;
        }
    }

    private void processSteerActions() {
        if (xAxisPercentage < ACTION_STEER_LEFT) {
            steerAction = SteerAction.STEER_LEFT;
        } else if (xAxisPercentage > ACRION_STEER_RIGHT) {
            steerAction = SteerAction.STEER_RIGHT;
        } else {
            steerAction = SteerAction.NONE;
        }
    }

    private void processButtonActions(Component c, Component.Identifier componentIdentifier) {
        if (!componentIdentifier.getName().matches("^[0-9]*$")) {
            return;
        }

        boolean isPressed = c.getPollData() != 0;
        processBoostAction(componentIdentifier, isPressed);
        processBrakeAction(componentIdentifier, isPressed);
    }

    private void processBoostAction(Component.Identifier componentIdentifier, boolean isPressed) {
        if (componentIdentifier == Component.Identifier.Button._0 && isPressed) {
            throttleAction = ThrottleAction.BOOST;
        } else if (componentIdentifier == Component.Identifier.Button._0 && !isPressed && throttleAction != ThrottleAction.ACCELERATE && throttleAction != ThrottleAction.REVERSE) {
            throttleAction = ThrottleAction.IDLE;
        }

    }

    private void processBrakeAction(Component.Identifier componentIdentifier, boolean isPressed) {
        if (componentIdentifier == Component.Identifier.Button._1 && isPressed) {
            handbrakeAction = HandbrakeAction.ACTIVE;
            return;
        } else if (componentIdentifier == Component.Identifier.Button._1 && !isPressed) {
            handbrakeAction = handbrakeAction.INACTIVE;
        }
    }

    /**
     * Given value of axis in percentage. Percentages increases from left/top to
     * right/bottom. If idle (in center) returns 50, if joystick axis is pushed
     * to the left/top edge returns 0 and if it's pushed to the right/bottom
     * returns 100.
     *
     * @return value of axis in percentage.
     */
    private int getAxisValueInPercentage(float axisValue) {
        return (int) (((2 - (1 - axisValue)) * 100) / 2);
    }

    public boolean isConnected() {
        return controller.poll();
    }

    ThrottleAction getThrottleAction() {
        return throttleAction;
    }

    HandbrakeAction getHandbrakeAction() {
        return handbrakeAction;
    }

    SteerAction getSteerAction() {
        return steerAction;
    }
}
