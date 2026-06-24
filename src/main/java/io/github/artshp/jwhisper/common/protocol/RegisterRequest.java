package io.github.artshp.jwhisper.common.protocol;

import java.util.UUID;

/**
 * User register request. User sends it when wants to register on server, i.e. reserve username.
 *
 * @param id message id
 * @param username username
 * @param publicSigningKey public signing key
 * @param publicEncryptionKey public encryption key
 * @param ownershipSignature signed username
 */
public record RegisterRequest(
        String id,
        String username,
        byte[] publicSigningKey,
        byte[] publicEncryptionKey,
        byte[] ownershipSignature
) implements WhisperMessage, Identifiable {

    /**
     * User register request. User sends it when wants to register on server, i.e. reserve username.
     *
     * @param username username
     * @param publicSigningKey public signing key
     * @param publicEncryptionKey public encryption key
     * @param ownershipSignature signed username
     */
    public RegisterRequest(
            String username,
            byte[] publicSigningKey,
            byte[] publicEncryptionKey,
            byte[] ownershipSignature)
    {
        this(UUID.randomUUID().toString(), username, publicSigningKey, publicEncryptionKey, ownershipSignature);
    }

    @Override
    public String getId() {
        return id;
    }
}
