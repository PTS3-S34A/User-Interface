package nl.soccar.ui.util;

import nl.soccar.ui.DisplayConstants;
import nl.soccar.ui.Main;

/**
 * Utility class which provides conversion methods between Physics and JavaFX
 * pixels, and provides methods to calculate car sizes.
 * 
 * @author PTS34A
 */
public final class PhysicsUtilities {

    private PhysicsUtilities() {
    }

    /**
     * Convert a JBox2D X-coordinate to a JavaFX pixel X-coordinate.
     *
     * @param x the JBox2D x-coodinate to convert.
     * @return The JavaFX x-coordinate.
     */
    public static float toPixelX(float x) {
        return x * getPixelsPerMeter();
    }

    /**
     * Convert a JBox2D Y-coordinate to a JavaFX pixel Y-coordinate.
     *
     * @param y the JBox2D y-coordinate to convert.
     * @return The JavaFX y-coordinate.
     */
    public static float toPixelY(float y) {
        return (DisplayConstants.MAP_HEIGHT - y) * getPixelsPerMeter();
    }

    /**
     * Converts a JBox2D width to a JavaFX pixel-width
     *
     * @param width the JBox2D width to convert.
     * @return The JavaFX pixel-width.
     */
    public static float toPixelWidth(float width) {
        return width * getPixelsPerMeter();
    }

    /**
     * Converts a JBox2D height to pixel-height
     *
     * @param height the JBox2D height to convert.
     * @return The JavaFX pixel-height.
     */
    public static float toPixelHeight(float height) {
        return height * getPixelsPerMeter();
    }

    /**
     * Calculates relative car height based on given width.
     * 
     * @param carWidth The width to calculate the height from.
     * @return The calculated height.
     */
    public static float calculateCarHeight(float carWidth) {
        return carWidth * 47 / 20;
    }

    /**
     * Calculates relative wheel width based on given car width.
     * 
     * @param carWidth The width to calculate the wheel width from.
     * @return The calculated wheel width.
     */
    public static float calculateWheelWidth(float carWidth) {
        return carWidth / 5;
    }

    /**
     * Calculates relative wheel height based on given car width
     * 
     * @param wheelWidth The wheel width to calculate the wheel height from.
     * @return The calculated wheel height.
     */
    public static float calculateWheelHeight(float wheelWidth) {
        return wheelWidth * 2;
    }
    
    /**
     * Method that calculates the number of pixels per meter based on the screen width of the user and the width of the map.
     * @return The number of pixels per meter based on the screen width of the user and the width of the map.
     */
    private static float getPixelsPerMeter() {
        return Main.getInstance().getStageWidth() / DisplayConstants.MAP_WIDTH;
    }
}
