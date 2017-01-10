package nl.soccar.ui.until;

import javafx.scene.image.Image;
import nl.soccar.library.enumeration.CarType;
import nl.soccar.library.enumeration.TeamColour;
import nl.soccar.ui.util.ImageUtilities;
import nl.soccar.ui.util.PhysicsUtilities;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * JUnit test that tests the nl.soccar.ui.util.PhysicsUtilities class.
 *
 * @author PTS34A
 */
public class PhysicsUtitlitesTest {

    @Test
    public void calculateCarHeightTest() {
        float height = 11.75F;
        float width = 5F;

        assertEquals(height, PhysicsUtilities.calculateCarHeight(width), 0.001F);
    }

    @Test
    public void calculateWheelWidthTest() {
        float carHeight = 10;
        float wheelWidth = 2;

        assertEquals(wheelWidth, PhysicsUtilities.calculateWheelWidth(carHeight), 0.001F);
    }

    @Test
    public void calculateWheelHeightTest() {
        float wheelHeight = 10;
        float wheelWidth = 5;

        assertEquals(wheelHeight, PhysicsUtilities.calculateWheelHeight(wheelWidth), 0.001F);
    }
}
