package io.github.artshp.jwhisper.common.crypto;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for {@link CertUtils} class.
 */
class CertUtilsTest {

    private static final Path CERTIFICATES_FOLDER = Path.of("src/test/resources/certificates");

    @Tag("unit")
    @ParameterizedTest
    @ValueSource(strings = {
            "google.com.crt",
            "plain_google.com.crt",
            "www.netflix.com.crt",
    })
    void testValidCertificateParsing(String certificateFilename) throws IOException {
        String pemCertificate = Files.readString(CERTIFICATES_FOLDER.resolve("valid", certificateFilename));

        Optional<X509Certificate> cert = CertUtils.parsePemCertificate(pemCertificate);
        assertTrue(cert.isPresent());
    }

    @Tag("unit")
    @ParameterizedTest
    @ValueSource(strings = {
            "plain_no_pem_google.com.crt",
            "empty.crt",
    })
    void testInvalidCertificateParsing(String certificateFilename) throws IOException {
        String pemCertificate = Files.readString(CERTIFICATES_FOLDER.resolve("invalid", certificateFilename));

        Optional<X509Certificate> cert = CertUtils.parsePemCertificate(pemCertificate);
        assertTrue(cert.isEmpty());
    }

    @Tag("unit")
    @ParameterizedTest
    @MethodSource("getDataFingerprints")
    void testValidFingerprint(byte[] data, String expectedFingerprint) {
        String computedFingerprint = CertUtils.getFingerprint(data);
        assertEquals(expectedFingerprint, computedFingerprint);
    }

    private static Stream<Arguments> getDataFingerprints() {
        return Stream.of(
                Arguments.of(
                        new byte[0],
                        "SHA-256:E3:B0:C4:42:98:FC:1C:14:9A:FB:F4:C8:99:6F:B9:24:27:AE:41:E4:64:9B:93:4C:A4:95:99:1B:78:52:B8:55"
                ),
                Arguments.of(
                        "".getBytes(StandardCharsets.UTF_8),
                        "SHA-256:E3:B0:C4:42:98:FC:1C:14:9A:FB:F4:C8:99:6F:B9:24:27:AE:41:E4:64:9B:93:4C:A4:95:99:1B:78:52:B8:55"
                ),
                Arguments.of(
                        "Hello".getBytes(StandardCharsets.UTF_8),
                        "SHA-256:18:5F:8D:B3:22:71:FE:25:F5:61:A6:FC:93:8B:2E:26:43:06:EC:30:4E:DA:51:80:07:D1:76:48:26:38:19:69"
                )
        );
    }

    @Tag("unit")
    @Test
    void testInvalidFingerprint() {
        assertThrows(NullPointerException.class, () -> CertUtils.getFingerprint((byte[]) null));
    }
}
