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

    public static InputController getInstance() {
        return INSTANCE;
    }

    public void initializeInput(Keyboard keyboard) {
        this.keyboard = keyboard;
        start();
    }

    private void start() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                poll();
            }
        }, 0, POLL_TIME);
    }

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

    private void pollKeyboard() {
        setThrottleAction(keyboard.getThrottleAction());
        setHandbrakeAction(keyboard.getHandbrakeAction());
        setSteerAction(keyboard.getSteerAction());
    }

    public List<Controller> getAllGamePadControllers() {
        Controller[] conttrollers = ControllerEnvironment.getDefaultEnvironment().getControllers();

        return Stream.of(conttrollers).filter(c -> c.getType() == Controller.Type.GAMEPAD).collect(Collectors.toList());
    }

    public void setGamePadController(Controller controller) {
        if (controller == null) {
            return;
        }

        this.gamePad = new GamePad(controller);
    }

    public ThrottleAction getThrottleAction() {
        return throttleAction;
    }

    void setThrottleAction(ThrottleAction throttleAction) {
        this.throttleAction = throttleAction;
    }

    public HandbrakeAction getHandbrakeAction() {
        return handbrakeAction;
    }

    void setHandbrakeAction(HandbrakeAction handbrakeAction) {
        this.handbrakeAction = handbrakeAction;
    }

    public SteerAction getSteerAction() {
        return steerAction;
    }

    void setSteerAction(SteerAction steerAction) {
        this.steerAction = steerAction;
    }

}
