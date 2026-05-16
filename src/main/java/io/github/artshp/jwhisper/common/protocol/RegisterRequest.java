package io.github.artshp.jwhisper.common.protocol;

/**
 * User register request. User sends it when wants to register on server,
 * i.e. reserve username and start session.
 *
 * @param username username
 * @param publicSigningKey public signing key
 * @param publicEncryptionKey public encryption key
 * @param ownershipSignature signed username
 */
public record RegisterRequest(
        String username,
        byte[] publicSigningKey,
        byte[] publicEncryptionKey,
        byte[] ownershipSignature
) implements WhisperMessage {
}
