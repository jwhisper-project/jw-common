package io.github.artshp.jwhisper.common.protocol;

public record UserPublicKeyResponse(
        String targetUsername,
        byte[] publicSigningKey,    // Ed25519
        byte[] publicEncryptionKey, // X25519
        boolean found
) implements WhisperMessage {
}
