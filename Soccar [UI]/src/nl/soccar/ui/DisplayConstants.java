package nl.soccar.ui;

/**
 * Class that contains constant values regarding to the user interface.
 *
 * @author PTS34A
 */
public final class DisplayConstants {

    /**
     * Application name constant.
     */
    public static final String APPLICATION_NAME = "Soccar";

    /**
     * Screen constants in pixels.
     */
    public static final float SCREEN_WIDTH = 1200.0F;
    public static final float SCREEN_HEIGHT = 800.0F;

    /**
     * Map image location constants.
     */
    public static final String LOCATION_TEXTURE_MOON = "resources/images/moon_texture.jpg";
    public static final String LOCATION_TEXTURE_DESERT = "resources/images/desert_texture.jpg";
    public static final String LOCATION_TEXTURE_GRASS = "resources/images/grass_texture.jpg";

    /**
     * Entity images properties
     */
    public static final String LOCATION_TEXTURE_BALL = "resources/images/ball_texture.png";

    /**
     * Stage constants.
     */
    public static final String LOCATION_STAGE_ICON = "resources/images/icon.png";

    private DisplayConstants() {
        /**
         * Constructor is intentionally set private, so that this class can
         * never be initialized.
         */
    }
}
