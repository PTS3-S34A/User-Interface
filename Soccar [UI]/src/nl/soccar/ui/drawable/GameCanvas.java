package nl.soccar.ui.drawable;

import nl.soccar.ui.drawable.Drawable;
import nl.soccar.library.Game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import nl.soccar.physics.GameEngine;
import nl.soccar.ui.drawable.PhysicsDrawable;

/**
 * A canvas keeps track of Drawables. It updates them and provides a way to draw
 * them. It also keeps track of all Physics, the Drawables (in turn) provide
 * stepping methodes for the World (Physics).
 *
 * @author PTS34A
 */
public abstract class GameCanvas {

    private final GameEngine engine;
    private final List<Drawable> drawables;

    /**
     * Initiates a new GameCanvas object. While initializing, the collections of
     * Drawables en Physics are also initialized.
     *
     * @param game The Game, not null, that will be used to interact with.
     */
    public GameCanvas(Game game) {
        engine = new GameEngine(game);

        drawables = new ArrayList<>();
    }

    /**
     * Adds a drawable to the list of drawables.
     *
     * @param drawable Drawable item, not null, that needs to be added to the
     * list of drawables.
     */
    public final void addDrawable(Drawable drawable) {
        drawables.add(drawable);
    }
    
    public final void addDrawable(PhysicsDrawable drawable) {
        drawables.add(drawable);
        engine.addWorldObject(drawable.getPhysicsModel());
    }

    /**
     * Removes a drawable out of the list of drawables.
     *
     * @param drawable Drawable item, not null, that needs to be removed from
     * the drawable list.
     */
    public final void removeDrawable(Drawable drawable) {
        drawables.remove(drawable);
    }
    
    public final void removeDrawable(PhysicsDrawable drawable) {
        drawables.remove(drawable);
        engine.removeWorldObject(drawable.getPhysicsModel());
    }

    public final GameEngine getGameEngine() {
        return engine;
    }

    /**
     * Gets all Drawables of this GameCanvas.
     *
     * @return An unmodifiable List, not null, of Drawables, not null.
     */
    public final List<Drawable> getDrawables() {
        return Collections.unmodifiableList(drawables);
    }

}
