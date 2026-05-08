package io.github.artshp.jwhisper.common.protocol;

public record RegisterRequest(
        String username,
        byte[] publicIdentityKey,
        byte[] usernameSignature
) implements WhisperMessage {
}
