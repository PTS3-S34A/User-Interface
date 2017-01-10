package nl.soccar.ui.until;

import javafx.scene.image.Image;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.ui.util.ImageUtilities;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * JUnit test that tests the nl.soccar.ui.util.ImageUtilities class.
 *
 * @author PTS34A
 */
public class ImageUtilitiesTest {

    private Image img;

    /**
     * Tests getting the red casual car.
     */
    @Test
    public void getCarImageRedTest() {
        img = new Image("resources/images/cars/casual_red_texture.png");
        assertEquals(img, ImageUtilities.getCarImage(CarType.CASUAL, TeamColour.RED));
    }

    /**
     * Tests getting the blue pickup car.
     */
    @Test
    public void getCarImageBlueTest() {
        img = new Image("resources/images/cars/pickup_blue_texture.png");
        assertEquals(img, ImageUtilities.getCarImage(CarType.PICKUP, TeamColour.BLUE));
    }

}
