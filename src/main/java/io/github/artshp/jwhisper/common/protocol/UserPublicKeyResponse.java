package io.github.artshp.jwhisper.common.protocol;

import java.util.UUID;

/**
 * Server public keys response. Server sends it as answer on {@link UserPublicKeyRequest}.
 *
 * @param id original message id
 * @param targetUsername target username
 * @param publicSigningKey target's public signing key if found, otherwise {@code null}
 * @param publicEncryptionKey target's encryption key if found, otherwise {@code null}
 * @param found whether public keys where found
 *              (which effectively means if user with given username is registered on server or not)
 */
public record UserPublicKeyResponse(
        String id,
        String targetUsername,
        byte[] publicSigningKey,
        byte[] publicEncryptionKey,
        boolean found
) implements WhisperMessage, Identifiable {

    /**
     * Server public keys response. Server sends it as answer on {@link UserPublicKeyRequest}.
     *
     * @param targetUsername target username
     * @param publicSigningKey target's public signing key if found, otherwise {@code null}
     * @param publicEncryptionKey target's encryption key if found, otherwise {@code null}
     * @param found whether public keys where found
     *              (which effectively means if user with given username is registered on server or not)
     */
    public UserPublicKeyResponse(
            String targetUsername,
            byte[] publicSigningKey,
            byte[] publicEncryptionKey,
            boolean found)
    {
        this(UUID.randomUUID().toString(), targetUsername, publicSigningKey, publicEncryptionKey, found);
    }

    @Override
    public String getId() {
        return id;
    }
}
