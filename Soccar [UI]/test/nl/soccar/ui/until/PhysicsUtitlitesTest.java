package nl.soccar.ui.until;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import nl.soccar.ui.util.PhysicsUtilities;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * JUnit test that tests the nl.soccar.ui.util.PhysicsUtilities class.
 *
 * @author PTS34A
 */
public class PhysicsUtitlitesTest {

    /**
     * Tests the private constructor.
     *
     * @throws Throwable Thrown when the private constructor is invoked.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void privateConstructorTest() throws Throwable {
        Constructor<PhysicsUtilities> constructor = (Constructor<PhysicsUtilities>) PhysicsUtilities.class.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        try {
            constructor.newInstance();
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        } catch (InstantiationException | IllegalArgumentException | IllegalAccessException e) {
            fail("Wrong exception type thrown.");
        }
    }

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
