package io.github.artshp.jwhisper.common.io;

import io.github.artshp.jwhisper.common.exception.InputRetryException;
import lombok.experimental.UtilityClass;

import java.io.Console;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

@UtilityClass
public class ConsoleUtils {

    private static final Console CONSOLE = System.console();

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";

    static {
        Objects.requireNonNull(CONSOLE, "console must be not null.");
    }

    public static char[] readNewPassword(String prompt, Predicate<char[]> validator, String errorMsg, int maxRetries)
            throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            char[] firstEntry = readPassword(prompt, validator, errorMsg, maxRetries);
            char[] secondEntry = readPassword("Confirm " + prompt, validator, errorMsg, maxRetries);

            if (Arrays.equals(firstEntry, secondEntry)) {
                Arrays.fill(secondEntry, '0');
                return firstEntry;
            }

            Arrays.fill(firstEntry, '0');
            Arrays.fill(secondEntry, '0');

            attempts++;
            error("Passwords do not match. (Attempts: " + attempts + "/" + maxRetries + ")");
        }

        throw new InputRetryException("Failed to establish a new password after " + maxRetries + " attempts.");
    }

    public static char[] readPassword(String prompt, Predicate<char[]> validator, String errorMsg, int maxRetries)
            throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            char[] password = CONSOLE.readPassword(CYAN + prompt + RESET);
            if (validator.test(password)) return password;

            attempts++;
            error(errorMsg + " (Attempts left: " + (maxRetries - attempts) + ")");
        }

        throw new InputRetryException("Too many failed attempts to enter a valid password.");
    }

    public static String readString(String prompt, Predicate<String> validator, String errorMsg, int maxRetries)
            throws InputRetryException {
        int attempts = 0;
        while (attempts < maxRetries) {
            String input = CONSOLE.readLine(CYAN + prompt + RESET).trim();
            if (validator.test(input)) return input;

            attempts++;
            error(errorMsg + " (Attempts left: " + (maxRetries - attempts) + ")");
        }

        throw new InputRetryException("Too many failed attempts for input: " + prompt);
    }

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

    private static void error(String msg) {
        System.out.println(RED + "✘ Error: " + msg + RESET);
    }
}
