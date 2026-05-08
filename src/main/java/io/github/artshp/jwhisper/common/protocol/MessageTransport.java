package io.github.artshp.jwhisper.common.protocol;

import lombok.extern.slf4j.Slf4j;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

import java.io.*;

@Slf4j
public class MessageTransport {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public void sendMessage(OutputStream out, Object message) throws IOException {
        DataOutputStream dos = new DataOutputStream(out);
        byte[] bytes = MAPPER.writeValueAsBytes(message);

        // 1. Write the length (4 bytes)
        dos.writeInt(bytes.length);

        // 2. Write the JSON data
        dos.write(bytes);
        dos.flush();
    }

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
