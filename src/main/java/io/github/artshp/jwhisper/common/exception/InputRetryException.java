package io.github.artshp.jwhisper.common.exception;

/**
 * Exception when user fails to provide valid input after given number of retries.
 */
public class InputRetryException extends Exception {

    /**
     * Constructs a new exception.
     * @see Exception#Exception()
     */
    public InputRetryException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message detail message
     * @see Exception#Exception(String)
     */
    public InputRetryException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * @param message detail message
     * @param cause cause
     * @see Exception#Exception(String, Throwable)
     */
    public InputRetryException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * {@code cause == null ? null : cause.toString())}.
     * @param cause cause
     * @see Exception#Exception(Throwable)
     */
    public InputRetryException(Throwable cause) {
        super(cause);
    }
}
