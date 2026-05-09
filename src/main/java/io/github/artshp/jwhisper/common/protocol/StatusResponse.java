package io.github.artshp.jwhisper.common.protocol;

public record StatusResponse(
        boolean success,
        String message
) implements WhisperMessage {
}
