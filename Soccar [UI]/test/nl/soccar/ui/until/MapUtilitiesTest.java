package nl.soccar.ui.until;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.shape.Rectangle;
import nl.soccar.ui.util.MapUtilities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test that tests the nl.soccar.ui.util.MapUtilities class.
 *
 * @author PTS34A
 */
public class MapUtilitiesTest {

    private Rectangle rec;

    /**
     * Instantiation of test object.
     */
    @Before
    public void setUp() {
        int xPos = 10;
        int yPos = 20;
        int width = 50;
        int height = 30;
        rec = new Rectangle(xPos, yPos, width, height);
    }

    /**
     * Tests the private constructor.
     *
     * @throws Throwable Thrown when the private constructor is invoked.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void privateConstructorTest() throws Throwable {
        Constructor<MapUtilities> constructor = (Constructor<MapUtilities>) MapUtilities.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
            fail("Wrong exception type thrown.");
        }
    }

    /**
     * Tests getting the X-center of a rectangle.
     */
    @Test
    public void getCentreXTest() {
        assertEquals(35F, MapUtilities.getCentreX(rec), 0.001D);
    }

    /**
     * Tests getting the Y-center of a rectangle.
     */
    @Test
    public void getCentreYTest() {
        assertEquals(35F, MapUtilities.getCentreY(rec), 0.001D);
    }
}
