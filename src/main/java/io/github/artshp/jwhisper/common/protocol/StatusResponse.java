package io.github.artshp.jwhisper.common.protocol;

/**
 * Server status response. This message is sent to inform client about success or failure of request.
 * @param success whether request was successfully processed or not
 * @param message status message
 */
public record StatusResponse(
        boolean success,
        String message
) implements WhisperMessage {
}
