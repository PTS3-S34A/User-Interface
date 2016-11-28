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
     * Ball constant in JBox2D units.
     */
    public static final float BALL_RADIUS = 2.5F;

    /**
     * Car constant in JBox2D units.
     */
    public static final float CAR_WIDTH = 3.0F;

    /**
     * Map constants in JBox2D units.
     */
    public static final float MAP_WIDTH = 160.0F;
    public static final float MAP_HEIGHT = 90.0F;
    public static final float SCOREBOARD_WIDTH = 10.0F;
    public static final float SCOREBOARD_HEIGHT = 6.0F;
    public static final float LINE_WIDTH = 0.2F;
    public static final float CENTRE_CIRCLE_SIZE = 13.75F;
    public static final float CENTRE_SPOT_SIZE = 1.5F;
    public static final float FIELD_MARGIN = 5.0F;
    public static final float BOX_WIDTH = 12.5F;
    public static final float BOX_HEIGHT = 32.5F;
    public static final float GOAL_WIDTH = 3.5F;
    public static final float GOAL_HEIGHT = BOX_HEIGHT / 1.5F;

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
