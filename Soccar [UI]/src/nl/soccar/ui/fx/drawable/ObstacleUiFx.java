package nl.soccar.ui.fx.drawable;

import javafx.scene.canvas.GraphicsContext;
import nl.soccar.library.Obstacle;
import nl.soccar.library.enumeration.ObstacleType;
import nl.soccar.physics.models.ObstaclePhysics;
import nl.soccar.ui.drawable.GameCanvas;
import nl.soccar.ui.drawable.PhysicsDrawable;
import nl.soccar.util.PhysicsUtilities;
import org.jbox2d.dynamics.World;

/**
 * An ObstacleUiFx object represents a JavaFX Drawable of an Obstacle. It keeps track of the Obstacle and ObstaclePhysics models and provides methods to draw and update the models.
 *
 * @author PTS34A
 */
public class ObstacleUiFx extends PhysicsDrawable<Obstacle, ObstaclePhysics> {

    public ObstacleUiFx(GameCanvas canvas, Obstacle obstacle) {
        this(canvas, obstacle, new ObstaclePhysics(obstacle, canvas.getGameEngine().getWorld()));
    }
    
    /**
     * Initiates a new ObstacleUiFx Object using the given parameters.
     *
     * @param canvas   The canvas on which this Obstacle is placed.
     * @param obstacle The model to keep track of.
     * @param physics  The physics-model to keep track of.
     */
    private ObstacleUiFx(GameCanvas canvas, Obstacle obstacle, ObstaclePhysics physics) {
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

        switch (obstacle.getObstacleType()) {
            default:
            case WALL:
                // The wall obstacle is never drawn, it is invisible.
                break;
        }

        context.restore(); // Restore canvas to display a rotated image.
    }

    /**
     * An ObstacleBuilder builds an obstacle for usage with the UI. It combines a model, physics-model and ui object and builds them implicitely.
     */
    public static class ObstacleBuilder {

        private final GameCanvas canvas;
        private final World world;

        private float x;
        private float y;
        private float degree;
        private float width;
        private float height;
        private ObstacleType type;

        /**
         * Initiates a new ObstacleBuilder with the given parameters.
         *
         * @param canvas The canvas that will be used to create the UI object.
         * @param world  The world that will be used to create the physics-model.
         */
        public ObstacleBuilder(GameCanvas canvas, World world) {
            this.canvas = canvas;
            this.world = world;
        }

        /**
         * Sets the x-position of this Obstacle.
         *
         * @param x The new x-position that will be used when building this obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder x(float x) {
            this.x = x;
            return this;
        }

        /**
         * Sets the y-position of this Obstacle.
         *
         * @param y The new y-position that will be used when building this obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder y(float y) {
            this.y = y;
            return this;
        }

        /**
         * Sets the angle of this Obstacle.
         *
         * @param degree The new angle that will be used when building this obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder degree(float degree) {
            this.degree = degree;
            return this;
        }

        /**
         * Sets the width of this Obstacle.
         *
         * @param width The new width that will be used when building this obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder width(float width) {
            this.width = width;
            return this;
        }

        /**
         * Sets the height of this Obstacle.
         *
         * @param height The new height that will be used when building this obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder height(float height) {
            this.height = height;
            return this;
        }

        /**
         * Sets the type of this Obstacle.
         *
         * @param type The new type that will be used when building this obstacle.
         * @return This ObstacleBuilder, for method chaining.
         */
        public ObstacleBuilder type(ObstacleType type) {
            this.type = type;
            return this;
        }

        /**
         * Builds an Obstacle-UI object, it combines a model and a physics-model to do so.
         *
         * @return The created ObstacleUiFx object.
         */
        public ObstacleUiFx build() {
            Obstacle obstacle = new Obstacle(x, y, degree, width, height, type);
            ObstaclePhysics obstaclePhysics = new ObstaclePhysics(obstacle, world);
            return new ObstacleUiFx(canvas, obstacle, obstaclePhysics);
        }

    }

}
