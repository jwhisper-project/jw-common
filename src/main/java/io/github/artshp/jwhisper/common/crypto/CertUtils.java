package io.github.artshp.jwhisper.common.crypto;

import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HexFormat;
import java.util.Optional;

import static io.github.artshp.jwhisper.common.crypto.SecurityUtils.*;

@Slf4j
public class CertUtils {

    public static Optional<X509Certificate> parsePemCertificate(String pem) {
        String cleanedPem = pem.strip()
                .replace("-----BEGIN CERTIFICATE-----", "-----BEGIN CERTIFICATE-----\n")
                .replace("-----END CERTIFICATE-----", "-----END CERTIFICATE-----\n");

        try (InputStream is = new ByteArrayInputStream(cleanedPem.getBytes())) {
            return Optional.of((X509Certificate) CERTIFICATE_FACTORY.generateCertificate(is));
        } catch (IOException | CertificateException e) {
            log.error("Failed to parse PEM certificate", e);
            return Optional.empty();
        }
    }

    public static String getFingerprint(X509Certificate certificate) {
        try {
            byte[] data = certificate.getEncoded();
            return getFingerprint(data);
        } catch (CertificateEncodingException e) {
            log.error("Failed to encode certificate", e);
            return null;
        }
    }

    public static String getFingerprint(PublicKey key) {
        return getFingerprint(key.getEncoded());
    }

    public static String getFingerprint(byte[] data) {
        byte[] hash = MESSAGE_DIGEST.digest(data);
        String hex = HexFormat.of()
                .withPrefix(":")
                .withUpperCase()
                .formatHex(hash);

        return HASH_ALGORITHM + hex;
    }
}
