package io.github.artshp.jwhisper.common.exception;

import lombok.experimental.StandardException;

/**
 * Exception when user fails to provide valid input after given number of retries.
 */
@StandardException
public class InputRetryException extends Exception {
}
