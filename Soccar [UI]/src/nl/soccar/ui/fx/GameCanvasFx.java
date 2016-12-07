package nl.soccar.ui.fx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.util.Duration;
import nl.soccar.library.Event;
import nl.soccar.library.Game;
import nl.soccar.library.Notification;
import nl.soccar.library.enumeration.GameStatus;
import nl.soccar.physics.PhysicsConstants;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.drawable.Drawable;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.fx.drawable.NotificationUiFx;
import nl.soccar.ui.input.Keyboard;

import java.time.LocalTime;
import java.util.List;

/**
 * This class is an extension to the GameCanvas class, it provides a way to run the game loop using JavaFX classes.
 *
 * @author PTS34A
 */
public class GameCanvasFx extends GameCanvas {

    private GraphicsContext context;
    private Timeline gameTimer;

    /**
     * Initiates a new GameCanvasFx object using the given parameters.
     *
     * @param context The context which can be used by Drawables to actually draw on a Canvas.
     */
    public GameCanvasFx(Game game, GraphicsContext context) {
        super(game);

        this.context = context;

        gameTimer = new Timeline();
        gameTimer.setCycleCount(Timeline.INDEFINITE);
        gameTimer.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0F / PhysicsConstants.UI_FPS), e -> render()));
        gameTimer.playFromStart();

        Canvas canvas = context.getCanvas();
        canvas.setOnKeyPressed(e -> Keyboard.setKeyPressed(e.getCode()));
        canvas.setOnKeyReleased(e -> Keyboard.setKeyReleased(e.getCode()));
    }

    /**
     * Clears the canvas and renders a frame by drawing all Drawable items.
     */
    private void render() {

        if (getGameEngine().getGame().getStatus() == GameStatus.RUNNING) {
            clear();
            List<Drawable> drawables = super.getDrawables();
            drawables.forEach(d -> d.draw(context));
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
