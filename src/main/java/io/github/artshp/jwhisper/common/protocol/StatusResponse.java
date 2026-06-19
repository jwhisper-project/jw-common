package io.github.artshp.jwhisper.common.protocol;

import java.util.UUID;

/**
 * Server status response. This message is sent to inform client about success or failure of request.
 *
 * @param id original message id
 * @param success whether request was successfully processed or not
 * @param message status message
 */
public record StatusResponse(
        String id,
        boolean success,
        String message
) implements WhisperMessage {

    /**
     * Server status response. This message is sent to inform client about success or failure of request.
     *
     * @param success whether request was successfully processed or not
     * @param message status message
     */
    public StatusResponse(boolean success, String message) {
        this(UUID.randomUUID().toString(), success, message);
    }
}
