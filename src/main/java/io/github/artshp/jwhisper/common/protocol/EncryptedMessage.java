package io.github.artshp.jwhisper.common.protocol;

/**
 * End-to-end encrypted message. User sends it to another user via relay server.
 * @param sender sender's username
 * @param recipient recipient's username
 * @param ephemeralPublicKey ephemeral public key, i.e. sender's one-time {@code X25519} key
 * @param nonce nonce, i.e. {@code AES-GCM} initial value
 * @param message encrypted message
 * @param signature signature of message signed using {@code Ed25519} key
 * @param timestamp timestamp of when message was sent
 */
public record EncryptedMessage(
        String sender,
        String recipient,
        byte[] ephemeralPublicKey,
        byte[] nonce,
        byte[] message,
        byte[] signature,
        long timestamp
) implements WhisperMessage {
}
