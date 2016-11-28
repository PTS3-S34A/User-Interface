package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import nl.soccar.library.Ball;
import nl.soccar.ui.DisplayConstants;
import nl.soccar.physics.models.BallPhysics;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.drawable.PhysicsDrawable;
import nl.soccar.util.PhysicsUtilities;

/**
 * A BallUiFx object represents a JavaFX Drawable of a Ball.
 * It keeps track of the Ball and BallPhysics models and provides methods to draw and
 * update the models.
 *
 * @author PTS34A
 */
public class BallUiFx extends PhysicsDrawable<Ball, BallPhysics> {

    private static final Image TEXTURE_BALL;

    static {
        TEXTURE_BALL = new Image(DisplayConstants.LOCATION_TEXTURE_BALL);
    }

    public BallUiFx(GameCanvas canvas, Ball ball) {
        this(canvas, ball, new BallPhysics(ball, canvas.getGameEngine().getWorld()));
    }
    
    /**
     * Initiates a new BallUiFx Object using the given parameters.
     *
     * @param canvas  The canvas on which this Ball is placed.
     * @param ball    The ball model to keep track of.
     * @param physics The physics model to keep track of.
     */
    public BallUiFx(GameCanvas canvas, Ball ball, BallPhysics physics) {
        super(canvas, ball, physics);
    }

    @Override
    public void draw(GraphicsContext context) {
        Ball ball = super.getModel();

        float radius = PhysicsUtilities.toPixelWidth(ball.getRadius());
        float x = PhysicsUtilities.toPixelX(ball.getX());
        float y = PhysicsUtilities.toPixelY(ball.getY());

        context.save(); // Save the canvas so we can draw a rotated rectangle
        context.translate(x, y); // Set the origin point of the rotation
        context.rotate(-ball.getDegree()); // Set the angle of the rotation

        // TODO: Find a solution for anti-aliasing
        context.drawImage(TEXTURE_BALL, -radius, -radius, radius * 2, radius * 2); // Draw the rectangle from the top left
        context.restore(); // Restore canvas to display a rotated image
    }

}
