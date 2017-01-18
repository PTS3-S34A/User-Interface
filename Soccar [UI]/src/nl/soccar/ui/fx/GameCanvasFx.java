package nl.soccar.ui.fx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import nl.soccar.library.Session;
import nl.soccar.library.enumeration.GameStatus;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.PhysicsConstants;
import nl.soccar.ui.drawable.Drawable;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.input.InputController;
import nl.soccar.ui.input.Keyboard;

import java.util.List;

/**
 * This class is an extension to the GameCanvas class, it provides a way to run
 * the game loop using JavaFX classes.
 *
 * @author PTS34A
 */
public class GameCanvasFx extends GameCanvas {

    private GraphicsContext context;
    private Timeline gameTimer;

    /**
     * Initiates a new GameCanvasFx object using the given parameters.
     *
     * @param session The session that runs on the canvas.
     * @param context The context which can be used by Drawables to actually
     * draw on a Canvas.
     */
    public GameCanvasFx(Session session, GraphicsContext context) {
        super(session);

        this.context = context;

        gameTimer = new Timeline();
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0F / PhysicsConstants.UI_FPS), e -> render()));
        gameTimer.playFromStart();

        Keyboard keyboard = new Keyboard();
        InputController controller = InputController.getInstance();
        controller.reset();

        if (controller.getAllGamePadControllers().size() > 0) {
            controller.setGamePadController(controller.getAllGamePadControllers().get(0));
        }

        controller.initializeInput(keyboard);

        Canvas canvas = context.getCanvas();
        canvas.setOnKeyPressed(e -> keyboard.setKeyPressed(e.getCode()));
        canvas.setOnKeyReleased(e -> keyboard.setKeyReleased(e.getCode()));
    }

    /**
     * Clears the canvas and renders a frame by drawing all Drawable items.
     */
    private void render() {
        GameEngine engine = super.getGameEngine();
        if (engine == null) {
            gameTimer.stop();
            return;
        }

        GameStatus gameStatus = engine.getGame().getStatus();
        if (gameStatus == GameStatus.RUNNING || gameStatus == GameStatus.PAUSED) {
            clear();

            List<Drawable> drawables = super.getDrawables();
            drawables.forEach(d -> d.draw(context));
        } else if (gameStatus == GameStatus.STOPPED) {
            gameTimer.stop();
        }
    }

    /**
     * Clears the canvas completely.
     */
    private void clear() {
        Canvas canvas = context.getCanvas();
        context.clearRect(0D, 0D, canvas.getWidth(), canvas.getHeight());
    }

}
