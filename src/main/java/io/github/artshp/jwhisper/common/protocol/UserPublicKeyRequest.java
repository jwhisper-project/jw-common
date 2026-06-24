package io.github.artshp.jwhisper.common.protocol;

import java.util.UUID;

/**
 * User public key fetch request. User sends it when wants to get public keys of some user.
 *
 * @param id message id
 * @param targetUsername target username
 */
public record UserPublicKeyRequest(
        String id,
        String targetUsername
) implements WhisperMessage, Identifiable {

    /**
     * User public key fetch request. User sends it when wants to get public keys of some user.
     *
     * @param targetUsername target username
     */
    public UserPublicKeyRequest(String targetUsername) {
        this(UUID.randomUUID().toString(), targetUsername);
    }

    @Override
    public String getId() {
        return id;
    }
}
