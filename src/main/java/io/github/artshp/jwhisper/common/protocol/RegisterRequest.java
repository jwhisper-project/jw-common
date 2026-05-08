package io.github.artshp.jwhisper.common.protocol;

public record RegisterRequest(
        String username,
        byte[] publicKey,
        byte[] usernameSignature
) implements WhisperMessage {
}
