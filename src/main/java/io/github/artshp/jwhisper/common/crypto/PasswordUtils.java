package io.github.artshp.jwhisper.common.crypto;

import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class PasswordUtils {

    public static char[] cleanPassword(char[] password) {
        Arrays.fill(password, '\0');
        return null;
    }
}
