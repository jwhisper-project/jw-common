package io.github.artshp.jwhisper.common.protocol;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.*;

/**
 * Service responsible for sending and receiving messages.
 */
@Slf4j
public class MessageTransport {

    /**
     * Object mapper needed to transform {@link WhisperMessage} to
     * {@code JSON} bytes and vice versa.
     */
    private static final ObjectMapper MAPPER = new ObjectMapper();

    /**
     * Create a new message transport service instance.
     */
    public MessageTransport() {
    }

    /**
     * Send message.
     * @param out output stream where to send message
     * @param message message to send
     * @throws IOException if failed to send message
     */
    public void sendMessage(OutputStream out, Object message) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] bytes = MAPPER.writeValueAsBytes(message);

        // 1. Write the length (4 bytes)
        dos.writeInt(bytes.length);

        // 2. Write the JSON data
        dos.write(bytes);
        dos.flush();
    }

    /**
     * Receive message.
     * @param in input stream from where to receive message
     * @param valueType type of message
     * @return received message
     * @param <T> type of message
     * @throws IOException if failed to receive message or message is malformed
     */
    public <T> T receiveMessage(InputStream in, Class<T> valueType) throws IOException {
        DataInputStream dis = new DataInputStream(in);

        // 1. Read the length (4 bytes)
        int length = dis.readInt();

        if (length < 0) {
            throw new IOException("Invalid message length: " + length);
        }
        if (length > 10_000) {
            throw new IOException("Message too large: " + length);
        }

        // 2. Read exactly N bytes
        byte[] payload = new byte[length];
        dis.readFully(payload);

        try {
            return MAPPER.readValue(payload, valueType);
        } catch (JacksonException e) {
            throw new IOException(e);
        }
    }
}
