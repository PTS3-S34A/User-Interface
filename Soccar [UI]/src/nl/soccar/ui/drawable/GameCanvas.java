package nl.soccar.ui.drawable;

import nl.soccar.library.Session;
import nl.soccar.physics.GameEngine;
import nl.soccar.physics.WorldObject;
import nl.soccar.physics.models.CarPhysics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A canvas keeps track of Drawables. It updates them and provides a way to draw
 * them. It also keeps track of all Physics, the Drawables (in turn) provide
 * stepping methodes for the World (Physics).
 *
 * @author PTS34A
 */
public abstract class GameCanvas {

    private final Session session;
    private final List<Drawable> drawables;
    private GameEngine engine;

    /**
     * Initiates a new GameCanvas object. While initializing, the collections of
     * Drawables en Physics are also initialized.
     *
     * @param session The Session, not null, that will be used by the GameEngine to interact with.
     */
    public GameCanvas(Session session) {
        this.session = session;

        drawables = new ArrayList<>();
    }

    /**
     * Initializes the GameEngine.
     */
    public void initialize() {
        engine = new GameEngine(session);
    }

    /**
     * Starts the GameEngine.
     */
    public void start() {
        engine.start();
    }

    /**
     * Stops the GameEngine.
     */
    public void stop() {
        engine.getGame().getMap().removeCars();

        engine.stop();
        engine = null;
    }

    /**
     * Adds a drawable to the list of drawables.
     *
     * @param drawable Drawable item, not null, that needs to be added to the
     * list of drawables.
     */
    public final void addDrawable(Drawable drawable) {
        synchronized (drawables) {
            drawables.add(drawable);
        }
    }

    /**
     * Adds a drawable to the list of drawables.
     *
     * @param drawable Drawable item, not null, that needs to be added to the
     * list of drawables.
     */
    public final void addDrawable(PhysicsDrawable drawable) {
        synchronized (drawables) {
            drawables.add(drawable);
        }

        WorldObject object = drawable.getPhysicsModel();
        if (object instanceof CarPhysics) {
            CarPhysics car = (CarPhysics) object;

            engine.addCar(car.getCar().getPlayer(), car);
        } else {
            engine.addWorldObject(drawable.getPhysicsModel());
        }
    }

    /**
     * Removes a drawable out of the list of drawables.
     *
     * @param drawable Drawable item, not null, that needs to be removed from
     * the drawable list.
     */
    public final void removeDrawable(Drawable drawable) {
        synchronized (drawables) {
            drawables.remove(drawable);
        }
    }

    /**
     * Removes a drawable out of the list of drawables.
     *
     * @param drawable Drawable item, not null, that needs to be removed from
     * the drawable list.
     */
    public final void removeDrawable(PhysicsDrawable drawable) {
        synchronized (drawables) {
            drawables.remove(drawable);
        }

        WorldObject object = drawable.getPhysicsModel();
        if (object instanceof CarPhysics) {
            CarPhysics car = (CarPhysics) object;

            engine.removeCar(car.getCar().getPlayer());
        } else {
            engine.removeWorldObject(drawable.getPhysicsModel());
        }
    }

    /**
     * Gets the GameEngine used by this Canvas.
     *
     * @return The GameEngine used by the Canvas.
     */
    public final GameEngine getGameEngine() {
        return engine;
    }

    /**
     * Gets all Drawables of this GameCanvas.
     *
     * @return An unmodifiable List, not null, of Drawables, not null.
     */
    public final List<Drawable> getDrawables() {
        synchronized (drawables) {
            return Collections.unmodifiableList(drawables);
        }
    }

}
