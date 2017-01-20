package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import nl.soccar.library.Obstacle;
import nl.soccar.physics.models.ObstaclePhysics;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.drawable.PhysicsDrawable;
import nl.soccar.ui.util.PhysicsUtilities;

/**
 * An ObstacleUiFx object represents a JavaFX Drawable of an Obstacle. It keeps track of the Obstacle and ObstaclePhysics models and provides methods to draw and update the models.
 *
 * @author PTS34A
 */
public class ObstacleUiFx extends PhysicsDrawable<Obstacle, ObstaclePhysics> {

    /**
     * Initializes a ObstacleUiFx object.
     * @param canvas The canvas that the obstacle will be drawn on.
     * @param obstacle The obstacle that will be drawn.
     */
    public ObstacleUiFx(GameCanvas canvas, Obstacle obstacle) {
        this(canvas, obstacle, new ObstaclePhysics(canvas.getGameEngine(), obstacle));
    }
    
    /**
     * Initiates a new ObstacleUiFx Object using the given parameters.
     *
     * @param canvas   The canvas on which this Obstacle is placed.
     * @param obstacle The model to keep track of.
     * @param physics  The physics-model to keep track of.
     */
    public ObstacleUiFx(GameCanvas canvas, Obstacle obstacle, ObstaclePhysics physics) {
        super(canvas, obstacle, physics);
    }

    @Override
    public void draw(GraphicsContext context) {
        Obstacle obstacle = super.getModel();

        float x = PhysicsUtilities.toPixelX(obstacle.getX());
        float y = PhysicsUtilities.toPixelY(obstacle.getY());

        context.save(); // Save the canvas so we can draw a rotated rectangle.

        context.translate(x, y); // Set the origin point of the rotation.
        context.rotate(-obstacle.getDegree()); // Set the angle of the rotation.

        context.restore(); // Restore canvas to display a rotated image.
    }

}
