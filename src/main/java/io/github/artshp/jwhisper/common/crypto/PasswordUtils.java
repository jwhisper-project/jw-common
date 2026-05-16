package io.github.artshp.jwhisper.common.crypto;

import java.util.Arrays;

/**
 * Password utils.
 */
public final class PasswordUtils {

    private PasswordUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Clean given password, i.e. replace it with {@code NULL} symbols.
     * @param password password to be cleaned
     * @return {@code null} value to be written instead of given password
     */
    public static char[] cleanPassword(char[] password) {
        Arrays.fill(password, '\0');
        return null;
    }
}
