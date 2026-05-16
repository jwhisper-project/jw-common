package io.github.artshp.jwhisper.common.protocol;

/**
 * User unregister request. User sends it when wants to unregister from server,
 * i.e. release username and finish session.
 */
public record UnregisterRequest() implements WhisperMessage {
}
