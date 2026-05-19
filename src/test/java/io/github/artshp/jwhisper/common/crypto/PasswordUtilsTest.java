package io.github.artshp.jwhisper.common.crypto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link PasswordUtils} class.
 */
class PasswordUtilsTest {

    private static final char[] RANDOM_BYTES = "jus4t ran@dom b_ytes of? passwor".toCharArray();

    private static char[] createCleanPassword(int length) {
        char[] password = new char[length];
        Arrays.fill(password, '\0');
        return password;
    }

    @Tag("unit")
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 8, 32})
    void testPasswordIsCleaned(int length) {
        char[] password = Arrays.copyOfRange(RANDOM_BYTES, 0, length);

        char[] result = PasswordUtils.cleanPassword(password);

        assertArrayEquals(createCleanPassword(length), password);
        assertNull(result);
    }

    @Tag("unit")
    @Test
    void testPasswordCleaningFailsForNull() {
        assertThrows(NullPointerException.class, () -> PasswordUtils.cleanPassword(null));
    }
}
