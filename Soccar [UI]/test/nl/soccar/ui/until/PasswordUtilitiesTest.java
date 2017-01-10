package nl.soccar.ui.until;

import junit.framework.Assert;
import nl.soccar.ui.util.PasswordUtilities;
import org.junit.Test;

/**
 * JUnit test that tests the nl.soccar.ui.util.PasswordUtilities class.
 *
 * @author PTS34A
 */
public class PasswordUtilitiesTest {

    /**
     * Tests generating a new hash.
     */
    @Test
    public void generateHashTest() {
        String password = "examplePassword";
        Assert.assertNotNull(PasswordUtilities.generateHash(password));

    }
}
