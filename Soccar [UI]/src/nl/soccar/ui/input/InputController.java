package nl.soccar.ui.input;

import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import nl.soccar.library.enumeration.HandbrakeAction;
import nl.soccar.library.enumeration.SteerAction;
import nl.soccar.library.enumeration.ThrottleAction;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller class that is responsible for the input handling of the Client.
 *
 * @author PTS34A
 */
public final class InputController {

    private static final InputController INSTANCE = new InputController();
    private static final int POLL_TIME = 50;
    private final Timer timer = new Timer(true);
    private ThrottleAction throttleAction;
    private HandbrakeAction handbrakeAction;
    private SteerAction steerAction;
    private Keyboard keyboard;
    private GamePad gamePad;

    private InputController() {
        throttleAction = ThrottleAction.IDLE;
        handbrakeAction = HandbrakeAction.INACTIVE;
        steerAction = SteerAction.NONE;
    }

    /**
     * Gets the instance of the InputController.
     *
     * @return The instance of the InputController.
     */
    public static InputController getInstance() {
        return INSTANCE;
    }

    /**
     * Initializes the Keyboard input device and starts the polling of the
     * connected input devices.
     *
     * @param keyboard The keyboard thats been given, not null.
     */
    public void initializeInput(Keyboard keyboard) {
        this.keyboard = keyboard;
        start();
    }

    /**
     * Starts the polling of the input divices.
     */
    private void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                poll();
            }
        }, 0, POLL_TIME);
    }

    /**
     * Polls the connected gamecontroller or keyboard and sets the new Action
     * values.
     */
    private void poll() {
        if (gamePad == null) {
            pollKeyboard();
        } else if (gamePad.isConnected()) {
            setThrottleAction(gamePad.getThrottleAction());
            setHandbrakeAction(gamePad.getHandbrakeAction());
            setSteerAction(gamePad.getSteerAction());
        } else {
            pollKeyboard();
        }
    }

    /**
     * Polls the Actions of the keyboard.
     */
    private void pollKeyboard() {
        setThrottleAction(keyboard.getThrottleAction());
        setHandbrakeAction(keyboard.getHandbrakeAction());
        setSteerAction(keyboard.getSteerAction());
    }

    /**
     * Gets all the connected Gamepadcontrollers of the Client.
     *
     * @return List<Controller> List of controllers that are connected to the
     * Client.
     */
    public List<Controller> getAllGamePadControllers() {
        Controller[] conttrollers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        return Stream.of(conttrollers).filter(c -> c.getType() == Controller.Type.GAMEPAD).collect(Collectors.toList());
    }

    /**
     * Sets the current gamepadcontroller to the given gamepadcontroller.
     *
     * @param controller The gamepadcontroller that the Client wants to use, not
     * null
     */
    public void setGamePadController(Controller controller) {
        if (controller == null) {
            return;
        }

        this.gamePad = new GamePad(controller);
    }

    /**
     * Gets the ThrottleAction of an input.
     *
     * @return ThrottleAction the ThrottleAction of the current input.
     */
    public ThrottleAction getThrottleAction() {
        return throttleAction;
    }

    /**
     * Sets the ThrottleAction of the input.
     *
     * @param throttleAction The current ThrottleAction, not null.
     */
    void setThrottleAction(ThrottleAction throttleAction) {
        this.throttleAction = throttleAction;
    }

    /**
     * Gets the HandbrakeAction of an input.
     *
     * @return HandbrakeAction the HandbrakeAction of the current input.
     */
    public HandbrakeAction getHandbrakeAction() {
        return handbrakeAction;
    }

    /**
     * Sets the HandBrakeAction of the input.
     *
     * @param throttleAction The current ThrottleAction, not null.
     */
    void setHandbrakeAction(HandbrakeAction handbrakeAction) {
        this.handbrakeAction = handbrakeAction;
    }

    /**
     * Gets the SteerAction of an input.
     *
     * @return SteerAction the SteerAction of the current input.
     */
    public SteerAction getSteerAction() {
        return steerAction;
    }

    /**
     * Sets the SteerAction of the input.
     *
     * @param SteerAction The current SteerAction, not null.
     */
    void setSteerAction(SteerAction steerAction) {
        this.steerAction = steerAction;
    }

}
