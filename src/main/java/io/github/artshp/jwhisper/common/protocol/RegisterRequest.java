package io.github.artshp.jwhisper.common.protocol;

public record RegisterRequest(
        String username,
        byte[] publicSigningKey,    // Ed25519
        byte[] publicEncryptionKey, // X25519
        byte[] ownershipSignature   // Sender signs his username with the signing key
) implements WhisperMessage {
}
