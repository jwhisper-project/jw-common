package io.github.artshp.jwhisper.common.protocol;

/**
 * User public key fetch request. User sends it when wants to get public keys of some user.
 * @param targetUsername target username
 */
public record UserPublicKeyRequest(
        String targetUsername
) implements WhisperMessage {
}
