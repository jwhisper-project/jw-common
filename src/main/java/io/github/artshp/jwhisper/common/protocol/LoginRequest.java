package io.github.artshp.jwhisper.common.protocol;

import java.util.UUID;

/**
 * User login request. User sends it when wants to log in on server, i.e. start session.
 *
 * @param id message id
 * @param username username
 * @param ownershipSignature signed username
 */
public record LoginRequest(
        String id,
        String username,
        byte[] ownershipSignature
) implements WhisperMessage, Identifiable {

    /**
     * User login request. User sends it when wants to log in on server, i.e. start session.
     *
     * @param username username
     * @param ownershipSignature signed username
     */
    public LoginRequest(
            String username,
            byte[] ownershipSignature)
    {
        this(UUID.randomUUID().toString(), username, ownershipSignature);
    }

    @Override
    public String getId() {
        return id;
    }
}
