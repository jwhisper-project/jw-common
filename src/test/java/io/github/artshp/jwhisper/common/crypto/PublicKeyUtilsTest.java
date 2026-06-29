package io.github.artshp.jwhisper.common.crypto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.util.HexFormat;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link PublicKeyUtils} class.
 */
class PublicKeyUtilsTest {

    @Tag("unit")
    @Test
    void testToSigningKeySuccessful() {
        String signing = "4C18BED1DA509E002D5D37DE5D8662CFDDE7EE6DC52A4CA7066A85DB9E2AEB91";

        try {
            PublicKey signingKey = PublicKeyUtils.newSigningPublicKey(HexFormat.of().parseHex(signing));
            assertNotNull(signingKey);
        } catch (InvalidKeySpecException e) {
            fail(e);
        }
    }

    @Tag("unit")
    @Test
    void testToSigningKeyBadKey() {
        String signing = "6C18BED1DA509E002D5D37DE5D8662CFDDE7EE6DC52A4CA7066A85DB9E2AEB91";

        assertThrows(IllegalArgumentException.class, () ->
                PublicKeyUtils.newSigningPublicKey(HexFormat.of().parseHex(signing))
        );
    }

    @Tag("unit")
    @Test
    void testToEncryptionKeySuccessful() {
        String encryption = "87901D674B28881532110E7B53624A307241E5AEAA483C2DBD6E7AD6D361F408";

        try {
            PublicKey encryptionKey = PublicKeyUtils.newEncryptionPublicKey(HexFormat.of().parseHex(encryption));
            assertNotNull(encryptionKey);
        } catch (InvalidKeySpecException e) {
            fail(e);
        }
    }

    @Tag("unit")
    @Test
    void testToEncryptionKeyBadKey() {
        String encryption = "87901D674B28881532110E7B53624A307241E5AEAA483C2DBD6E7AD6D361F40";

        assertThrows(IllegalArgumentException.class, () ->
                PublicKeyUtils.newEncryptionPublicKey(HexFormat.of().parseHex(encryption))
        );
    }
}
