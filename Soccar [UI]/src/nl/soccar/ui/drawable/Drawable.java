package nl.soccar.ui.drawable;

import javafx.scene.canvas.GraphicsContext;

/**
 * A Drawable is an Object that can be drawn on the screen. It keeps up with a
 * model. The model represents an object which is connected to this Drawable,
 * such as a Car.
 *
 * @author PTS34A
 * @param <M> The model connected to this Drawable.
 */
public abstract class Drawable<M> {

    private final GameCanvas canvas;
    private final M model;

    /**
     * Initializes a Drawable object.
     * 
     * @param canvas The gameCanvas for the object, not null.
     * @param model The model(type) of drawable, not null.
     */
    public Drawable(GameCanvas canvas, M model) {
        this.canvas = canvas;
        this.model = model;
    }

    /**
     * Draws this Drawable object on the graphics context that is passed as an
     * argument.
     *
     * @param context The drawable graphics context.
     */
    public abstract void draw(GraphicsContext context);

    /**
     * Gets the GameCanvas on which this drawable is placed.
     *
     * @return The GameCanvas, not null, on which this drawable is placed.
     */
    public final GameCanvas getGameCanvas() {
        return canvas;
    }

    /**
     * Gets the model assosciated with this Drawable.
     *
     * @return This model, may be null if none specified.
     */
    public final M getModel() {
        return model;
    }

}
