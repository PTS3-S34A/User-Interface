package nl.soccar.ui.until;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import nl.soccar.ui.util.PasswordUtilities;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import org.junit.Test;

/**
 * JUnit test that tests the nl.soccar.ui.util.PasswordUtilities class.
 *
 * @author PTS34A
 */
public class PasswordUtilitiesTest {

    /**
     * Tests the private constructor.
     *
     * @throws Throwable Thrown when the private constructor is invoked.
     */
    @Test(expected = UnsupportedOperationException.class)
    public void privateConstructorTest() throws Throwable {
        Constructor<PasswordUtilities> constructor = (Constructor<PasswordUtilities>) PasswordUtilities.class.getDeclaredConstructors()[0];
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
     * Tests generating a new hash.
     */
    @Test
    public void generateHashTest() {
        String password = "examplePassword";
        assertNotNull(PasswordUtilities.generateHash(password));
    }

}
