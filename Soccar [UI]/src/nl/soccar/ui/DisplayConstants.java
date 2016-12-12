package nl.soccar.ui;

import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

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
    public static final String LOCATION_TEXTURE_CHRISTMAS = "resources/images/christmas_texture.jpg";
    public static final String LOCATION_TEXTURE_ICE = "resources/images/ice_texture.jpg";
    public static final String LOCATION_TEXTURE_GRASS = "resources/images/grass_texture.jpg";

    /**
     * Notification constants
     */
    public static final Font NOTIFICATION_FONT = Font.font("Arial", FontWeight.BOLD, 80);
    public static final Color NOTIFICATION_FILL = Color.WHITE;
    public static final Color NOTIFICATION_STROKE = Color.rgb(32, 32, 32);
    public static final float NOTIFICATION_OUTLINE_WIDTH = 4.0F;
    public static final int NOTIFICATION_DISPLAY_TIME = 2;

    /**
     * Scoreboard constants
     */
    public static final float SCOREBOARD_WIDTH = 20.0F;
    public static final float SCOREBOARD_HEIGHT = 6.0F;
    public static final Font SCOREBOARD_FONT = Font.font("Arial", FontWeight.LIGHT, 35);
    public static final Color SCOREBOARD_BG_FILL = Color.rgb(64, 64, 64);
    public static final Color SCOREBOARD_TEXT_FILL = Color.WHITE;
    public static final Color SCOREBOARD_BG_STROKE = Color.rgb(32, 32, 32);
    public static final Color SCOREBOARD_TEXT_STROKE = Color.rgb(192, 192, 192);
    public static final float SCOREBOARD_BG_OUTLINE_WIDTH = 4.0F;
    public static final float SCOREBOARD_TEXT_OUTLINE_WIDTH = 2.0F;

    /**
     * Boost constants
     */
    public static final float BOOST_TRAIL_WIDTH = 10.0F;
    public static final float BOOST_METER_X = 0.0F;
    public static final float BOOST_METER_Y = 1.0F;
    public static final float BOOST_METER_WIDTH = 160.0F;
    public static final float BOOST_METER_HEIGHT = 1.0F;
    public static final Color BOOST_METER_STROKE_COLOR = Color.TRANSPARENT;
    public static final Color BOOST_METER_FILL_COLOR = Color.rgb(30, 30, 230);

    /**
     * Entity images properties
     */
    public static final String LOCATION_TEXTURE_SNOWBALL = "resources/images/snowball_texture.png";
    public static final String LOCATION_TEXTURE_PUCK = "resources/images/puck_texture.png";
    public static final String LOCATION_TEXTURE_FOOTBALL = "resources/images/football_texture.png";

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
