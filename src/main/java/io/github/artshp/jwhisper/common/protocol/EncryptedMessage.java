package io.github.artshp.jwhisper.common.protocol;

public record EncryptedMessage(
        String sender,
        String recipient,
        byte[] ephemeralPublicKey, // sender's one-time X25519 key
        byte[] nonce,              // AES-GCM IV
        byte[] message,            // the encrypted message
        byte[] signature,          // signed sender's Ed25519 key
        long timestamp
) implements WhisperMessage {
}
