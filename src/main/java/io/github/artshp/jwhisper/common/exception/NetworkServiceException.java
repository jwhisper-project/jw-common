package io.github.artshp.jwhisper.common.exception;

/**
 * Exception when network server fails to process request.
 */
public class NetworkServiceException extends Exception {

    /**
     * Constructs a new exception.
     * @see Exception#Exception()
     */
    public NetworkServiceException() {
        super();
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message detail message
     * @see Exception#Exception(String)
     */
    public NetworkServiceException(String message) {
        super(message);
    }

    /**
     * Constructs a new exception with the specified detail message and cause.
     * @param message detail message
     * @param cause cause
     * @see Exception#Exception(String, Throwable)
     */
    public NetworkServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new exception with the specified cause and a detail message of
     * {@code cause == null ? null : cause.toString())}.
     * @param cause cause
     * @see Exception#Exception(Throwable)
     */
    public NetworkServiceException(Throwable cause) {
        super(cause);
    }
}
