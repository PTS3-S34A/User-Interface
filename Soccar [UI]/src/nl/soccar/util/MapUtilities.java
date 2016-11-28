package nl.soccar.util;

import javafx.scene.shape.Rectangle;

/**
 * Utility class that provides methods regarding the Map. It provides methods for calculating goal placement and sizes.
 *
 * @author PTS34A
 */
public final class MapUtilities {

    private MapUtilities() {
    }
    
    /**
     * Method that calculates the center X-coordinate of the given rectangle.
     *
     * @param rectangle The rectangle of which the center X-coordinate needs to be calculated.
     * @return The center X-coordinate of the given rectangle.
     */
    public static float getCentreX(Rectangle rectangle) {
        return (float) (rectangle.getX() + rectangle.getWidth() / 2);
    }

    /**
     * Method that calculates the center Y-coordinate of the given rectangle.
     *
     * @param rectangle The rectangle of which the center Y-coordinate needs to be calculated.
     * @return The center Y-coordinate of the given rectangle.
     */
    public static float getCentreY(Rectangle rectangle) {
        return (float) (rectangle.getY() + rectangle.getHeight() / 2);
    }

}
