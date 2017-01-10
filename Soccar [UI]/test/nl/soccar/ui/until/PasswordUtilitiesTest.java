/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.soccar.ui.until;

import junit.framework.Assert;
import nl.soccar.ui.util.PasswordUtilities;
import org.junit.Test;

/**
 *
 * @author Luuk
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
