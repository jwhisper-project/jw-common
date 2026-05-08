package io.github.artshp.jwhisper.common.io;

import io.github.artshp.jwhisper.common.crypto.CertUtils;
import io.github.artshp.jwhisper.common.exception.InputRetryException;
import lombok.experimental.UtilityClass;

import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.function.Predicate;

@UtilityClass
public class UserInputUtils {

    private static final int DEFAULT_MAX_RETRIES = 3;

    private static final Predicate<char[]> DEFAULT_PASSWORD_VALIDATOR = password -> password.length >= 6;
    private static final Predicate<String> DEFAULT_USERNAME_VALIDATOR = username -> username.length() >= 4;

    private static final String DEFAULT_PASSWORD_ERROR_MESSAGE = "Password length should be at least 6.";
    private static final String DEFAULT_USERNAME_ERROR_MESSAGE = "Username length should be at least 4.";

    public static char[] readNewPassword() throws InputRetryException {
        return ConsoleUtils.readNewPassword(
                "Password: ",
                DEFAULT_PASSWORD_VALIDATOR,
                DEFAULT_PASSWORD_ERROR_MESSAGE,
                DEFAULT_MAX_RETRIES
        );
    }

    public static char[] readPassword() throws InputRetryException {
        return ConsoleUtils.readPassword(
                "Password: ",
                DEFAULT_PASSWORD_VALIDATOR,
                DEFAULT_PASSWORD_ERROR_MESSAGE,
                DEFAULT_MAX_RETRIES
        );
    }

    public static String readUsername() throws InputRetryException {
        return ConsoleUtils.readString(
                "Username: ",
                DEFAULT_USERNAME_VALIDATOR,
                DEFAULT_USERNAME_ERROR_MESSAGE,
                DEFAULT_MAX_RETRIES
        );
    }

    public static int readPort() throws InputRetryException {
        return ConsoleUtils.readInt(
                "Port: ",
                0,
                65535,
                DEFAULT_MAX_RETRIES
        );
    }

    public static String readHostname() throws InputRetryException {
        String hostname = ConsoleUtils.readString(
                "Hostname: ",
                host -> !host.isBlank(),
                "Hostname should not be blank.",
                DEFAULT_MAX_RETRIES
        );

        return hostname.isEmpty() ? "localhost" : hostname;
    }

    public static Optional<X509Certificate> readCertificate() throws InputRetryException {
        String cert = ConsoleUtils.readString(
                "Enter certificate (single line, with PEM boundaries): ",
                c -> c.startsWith("-----BEGIN CERTIFICATE-----") && c.endsWith("-----END CERTIFICATE-----"),
                "Certificate should be in PEM format.",
                DEFAULT_MAX_RETRIES
        );
        return CertUtils.parsePemCertificate(cert);
    }

    public static boolean askYesNo(String prompt) throws InputRetryException {
        return ConsoleUtils.readBoolean(prompt, DEFAULT_MAX_RETRIES);
    }
}
