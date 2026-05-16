package io.github.artshp.jwhisper.common.protocol;

/**
 * Server public keys response. Server sends it as answer on {@link UserPublicKeyRequest}.
 * @param targetUsername target username
 * @param publicSigningKey target's public signing key if found, otherwise {@code null}
 * @param publicEncryptionKey target's encryption key if found, otherwise {@code null}
 * @param found whether public keys where found
 *              (which effectively means if user with given username is registered on server or not)
 */
public record UserPublicKeyResponse(
        String targetUsername,
        byte[] publicSigningKey,
        byte[] publicEncryptionKey,
        boolean found
) implements WhisperMessage {
}
