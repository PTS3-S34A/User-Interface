package nl.soccar.ui.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Utilities class for password authentication that provides a method for
 * hashing a password.
 *
 * @author PTS34A
 */
public final class PasswordUtilities {

    private static final Logger LOGGER = Logger.getLogger(PasswordUtilities.class.getSimpleName());

    private static MessageDigest digest;

    static {
        try {
            digest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            LOGGER.log(Level.WARNING, "Failed to generate Hash.", e);
        }
    }

    /**
     * Constructor that is intentionally marked private so a PasswordUtilities
     * object can never be initiated outside this class.
     */
    private PasswordUtilities() {
    }

    /**
     * Generates the password hash.
     * 
     * @param password, the password the hash should be made for, not null.
     * @return byte array, the password hash.
     */
    public static byte[] generateHash(String password) {
        digest.reset();
        digest.update(password.getBytes());

        return digest.digest();
    }

}
