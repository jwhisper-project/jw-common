package io.github.artshp.jwhisper.common.protocol;

public record EncryptedMessage(
        String sender,
        String recipient,
        byte[] message,
        byte[] signature,
        long timestamp
) implements WhisperMessage {
}
