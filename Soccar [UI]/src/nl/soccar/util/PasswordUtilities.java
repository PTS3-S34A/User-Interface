package nl.soccar.util;

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

    public static byte[] generateHash(String password) {
        byte[] hash = new byte[64];
        digest.reset();

        digest.update(password.getBytes());
        hash = digest.digest();

        return hash;
    }

}
