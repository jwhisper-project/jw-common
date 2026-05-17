package io.github.artshp.jwhisper.common.io;

import io.github.artshp.jwhisper.common.exception.InputRetryException;

import java.io.Console;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * Console utils. Provides unified IO interface for user input.
 */
public final class ConsoleUtils {

    /**
     * System console.
     */
    private static final Console CONSOLE = System.console();

    /** Reset color marker */
    private static final String RESET = "\u001B[0m";

    /** Set red color marker */
    private static final String RED = "\u001B[31m";

    /** Set cyan color marker */
    private static final String CYAN = "\u001B[36m";

    static {
        Objects.requireNonNull(CONSOLE, "console must be not null.");
    }

    /**
     * Constructor to prohibit instantiating.
     */
    private ConsoleUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Read a new password with necessary confirmation.
     * @param prompt prompt to print
     * @param validator password validator
     * @param errorMessage error message
     * @param maxRetries number of retries
     * @return password provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static char[] readNewPassword(String prompt, Predicate<char[]> validator, String errorMessage, int maxRetries)
            throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            char[] firstEntry = readPassword(prompt, validator, errorMessage, maxRetries);
            char[] secondEntry = readPassword("Confirm " + prompt, validator, errorMessage, maxRetries);

            if (Arrays.equals(firstEntry, secondEntry)) {
                Arrays.fill(secondEntry, '\0');
                return firstEntry;
            }

            Arrays.fill(firstEntry, '\0');
            Arrays.fill(secondEntry, '\0');

            attempts++;
            error("Passwords do not match. (Attempts: " + attempts + "/" + maxRetries + ")");
        }

        throw new InputRetryException("Failed to establish a new password after " + maxRetries + " attempts.");
    }

    /**
     * Read a new password without confirmation.
     * @param prompt prompt to print
     * @param validator password validator
     * @param errorMessage error message
     * @param maxRetries number of retries
     * @return password provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static char[] readPassword(String prompt, Predicate<char[]> validator, String errorMessage, int maxRetries)
            throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            char[] password = CONSOLE.readPassword(CYAN + prompt + RESET);
            if (validator.test(password)) return password;

            attempts++;
            error(errorMessage + " (Attempts left: " + (maxRetries - attempts) + ")");
        }

        throw new InputRetryException("Too many failed attempts to enter a valid password.");
    }

    /**
     * Read text input.
     * @param prompt prompt to print
     * @param validator input validator
     * @param errorMessage error message
     * @param maxRetries number of retries
     * @return input provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static String readString(String prompt, Predicate<String> validator, String errorMessage, int maxRetries)
            throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            String input = CONSOLE.readLine(CYAN + prompt + RESET).trim();
            if (validator.test(input)) return input;

            attempts++;
            error(errorMessage + " (Attempts left: " + (maxRetries - attempts) + ")");
        }

        throw new InputRetryException("Too many failed attempts for input: " + prompt);
    }

    /**
     * Read integer input.
     * @param prompt prompt to print
     * @param min minimum value
     * @param max maximum value
     * @param maxRetries number of retries
     * @return integer provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static int readInt(String prompt, int min, int max, int maxRetries)
            throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            try {
                int val = Integer.parseInt(
                        CONSOLE.readLine(CYAN + prompt + " (" + min + "-" + max + "): " + RESET).trim()
                );
                if (val >= min && val <= max) return val;
                error("Value must be between " + min + " and " + max);
            } catch (NumberFormatException e) {
                error("Please enter a valid number.");
            }

            attempts++;
            error("Attempts left: " + (maxRetries - attempts));
        }

        throw new InputRetryException("Too many failed attempts to enter a valid number.");
    }

    /**
     * Read boolean input, i.e. ask {@code Yes}/{@code No} question.
     * @param prompt prompt to print
     * @param maxRetries number of retries
     * @return boolean answer provided by user
     * @throws InputRetryException if number of retries exceeded
     */
    public static boolean readBoolean(String prompt, int maxRetries) throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            String input = CONSOLE.readLine(CYAN + prompt + " [y/n]: " + RESET).trim().toLowerCase();

            if (input.equals("y") || input.equals("yes")) {
                return true;
            } else if (input.equals("n") || input.equals("no")) {
                return false;
            }

            attempts++;
            error("Invalid input. Please enter 'y' or 'n'. (Attempts left: " + (maxRetries - attempts) + ")");
        }

        throw new InputRetryException("Failed to get a valid yes/no response.");
    }

    /**
     * Print error message.
     * @param message message to print
     */
    private static void error(String message) {
        System.out.println(RED + "✘ Error: " + message + RESET);
    }
}
