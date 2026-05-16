package io.github.artshp.jwhisper.common.exception;

/**
 * Exception for issues with config file.
 */
public class ConfigFileException extends RuntimeException {

    /**
     * Constructs a new exception.
     * @see RuntimeException#RuntimeException()
     */
    public ConfigFileException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message detail message
     * @see RuntimeException#RuntimeException(String)
     */
    public ConfigFileException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * @param message detail message
     * @param cause cause
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public ConfigFileException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * {@code cause == null ? null : cause.toString())}.
     * @param cause cause
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public ConfigFileException(Throwable cause) {
        super(cause);
    }
}
