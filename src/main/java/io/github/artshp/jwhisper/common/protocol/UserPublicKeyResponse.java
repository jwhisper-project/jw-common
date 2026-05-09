package io.github.artshp.jwhisper.common.protocol;

public record UserPublicKeyResponse(
        String targetUsername,
        byte[] targetPublicKey,
        boolean found
) implements WhisperMessage {
}
