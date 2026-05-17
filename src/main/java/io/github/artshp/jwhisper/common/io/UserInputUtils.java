package io.github.artshp.jwhisper.common.io;

import io.github.artshp.jwhisper.common.crypto.CertUtils;
import io.github.artshp.jwhisper.common.exception.InputRetryException;

import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * User input utils. Wrapper over {@link ConsoleUtils} methods.
 */
public final class UserInputUtils {

    /**
     * Default number of retries.
     */
    private static final int DEFAULT_MAX_RETRIES = 3;

    /**
     * Default password validator. Validates only password length.
     */
    private static final Predicate<char[]> DEFAULT_PASSWORD_VALIDATOR = password -> password.length >= 6;

    /**
     * Default username validator. Validates only username length.
     */
    private static final Predicate<String> DEFAULT_USERNAME_VALIDATOR = username -> username.length() >= 4;

    /**
     * Default message when password validation failed.
     * @see #DEFAULT_PASSWORD_VALIDATOR
     */
    private static final String DEFAULT_PASSWORD_ERROR_MESSAGE = "Password length should be at least 6.";

    /**
     * Default message when username validation failed.
     * @see #DEFAULT_USERNAME_VALIDATOR
     */
    private static final String DEFAULT_USERNAME_ERROR_MESSAGE = "Username length should be at least 4.";

    /**
     * Constructor to prohibit instantiating.
     */
    private UserInputUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Read a new password with confirmation.
     * @return new password provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static char[] readNewPassword() throws InputRetryException {
        return ConsoleUtils.readNewPassword(
                "Password: ",
                DEFAULT_PASSWORD_VALIDATOR,
                DEFAULT_PASSWORD_ERROR_MESSAGE,
                DEFAULT_MAX_RETRIES
        );
    }

    /**
     * Read a password without confirmation.
     * @return password provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static char[] readPassword() throws InputRetryException {
        return ConsoleUtils.readPassword(
                "Password: ",
                DEFAULT_PASSWORD_VALIDATOR,
                DEFAULT_PASSWORD_ERROR_MESSAGE,
                DEFAULT_MAX_RETRIES
        );
    }

    /**
     * Read username.
     * @return username provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static String readUsername() throws InputRetryException {
        return ConsoleUtils.readString(
                "Username: ",
                DEFAULT_USERNAME_VALIDATOR,
                DEFAULT_USERNAME_ERROR_MESSAGE,
                DEFAULT_MAX_RETRIES
        );
    }

    /**
     * Read port.
     * @return port provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static int readPort() throws InputRetryException {
        return ConsoleUtils.readInt(
                "Port: ",
                0,
                65535,
                DEFAULT_MAX_RETRIES
        );
    }

    /**
     * Read hostname.
     * @return hostname provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static String readHostname() throws InputRetryException {
        String hostname = ConsoleUtils.readString(
                "Hostname: ",
                host -> !host.isBlank(),
                "Hostname should not be blank.",
                DEFAULT_MAX_RETRIES
        );

        return hostname.isEmpty() ? "localhost" : hostname;
    }

    /**
     * Read certificate.
     * @return certificate provided by user, otherwise {@link Optional#empty()} if failed to parse certificate
     * @throws InputRetryException if number of retries exceeded
     */
    public static Optional<X509Certificate> readCertificate() throws InputRetryException {
        String cert = ConsoleUtils.readString(
                "Enter certificate (single line, with PEM boundaries): ",
                c -> c.startsWith("-----BEGIN CERTIFICATE-----") && c.endsWith("-----END CERTIFICATE-----"),
                "Certificate should be in PEM format.",
                DEFAULT_MAX_RETRIES
        );
        return CertUtils.parsePemCertificate(cert);
    }

    /**
     * Ask {@code Yes}/{@code No} question.
     * @param prompt prompt to input
     * @return boolean answer provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static boolean askYesNo(String prompt) throws InputRetryException {
        return ConsoleUtils.readBoolean(prompt, DEFAULT_MAX_RETRIES);
    }
}
