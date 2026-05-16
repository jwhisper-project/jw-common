package io.github.artshp.jwhisper.common.protocol;

public record UserPublicKeyRequest(
        String targetUsername
) implements WhisperMessage {
}
