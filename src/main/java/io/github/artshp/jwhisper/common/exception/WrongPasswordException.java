package io.github.artshp.jwhisper.common.exception;

/**
 * Exception for wrong provided password.
 */
public class WrongPasswordException extends RuntimeException {

    /**
     * Constructs a new exception.
     * @see RuntimeException#RuntimeException()
     */
    public WrongPasswordException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message detail message
     * @see RuntimeException#RuntimeException(String)
     */
    public WrongPasswordException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * @param message detail message
     * @param cause cause
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public WrongPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * {@code cause == null ? null : cause.toString())}.
     * @param cause cause
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public WrongPasswordException(Throwable cause) {
        super(cause);
    }
}
