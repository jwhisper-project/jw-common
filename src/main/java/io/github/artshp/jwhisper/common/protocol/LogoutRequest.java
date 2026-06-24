package io.github.artshp.jwhisper.common.protocol;

import java.util.UUID;

/**
 * User logout request. User sends it when wants to log out from server, i.e. finish session.
 *
 * @param id message id
 */
public record LogoutRequest(
        String id
) implements WhisperMessage, Identifiable {

    /**
     * User logout request. User sends it when wants to log out from server, i.e. finish session.
     */
    public LogoutRequest() {
        this(UUID.randomUUID().toString());
    }

    @Override
    public String getId() {
        return id;
    }
}
