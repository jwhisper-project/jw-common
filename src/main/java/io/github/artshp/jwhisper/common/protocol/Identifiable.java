package io.github.artshp.jwhisper.common.protocol;

/**
 * Interface for messages having id.
 */
public interface Identifiable {

    /**
     * Get id of message.
     * @return id
     */
    String getId();
}
