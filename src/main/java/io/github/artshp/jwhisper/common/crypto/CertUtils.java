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

/**
 * Certificate utils.
 */
@Slf4j
public final class CertUtils {

    private CertUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Parse PEM certificate.
     * @param pem certificate in PEM format (with {@code CERTIFICATE} label used)
     * @return parsed certificate in {@code X509} format, otherwise {@link Optional#empty()} if fail to parse
     * @see X509Certificate
     * @see <a href="https://en.wikipedia.org/wiki/Privacy-Enhanced_Mail">PEM definition</a>
     */
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

    /**
     * Get fingerprint of given certificate
     * @param certificate certificate to compute fingerprint for
     * @return certificate's fingerprint in HEX format
     * @see #getFingerprint(byte[])
     */
    public static String getFingerprint(X509Certificate certificate) {
        try {
            byte[] data = certificate.getEncoded();
            return getFingerprint(data);
        } catch (CertificateEncodingException e) {
            log.error("Failed to encode certificate", e);
            return null;
        }
    }

    /**
     * Get fingerprint of given public key
     * @param key public key to compute fingerprint for
     * @return public key's fingerprint in HEX format
     * @see #getFingerprint(byte[])
     */
    public static String getFingerprint(PublicKey key) {
        return getFingerprint(key.getEncoded());
    }

    /**
     * Get fingerprint of given data
     * @param data data to compute fingerprint for
     * @return data fingerprint in HEX format
     */
    public static String getFingerprint(byte[] data) {
        byte[] hash = MESSAGE_DIGEST.digest(data);
        String hex = HexFormat.of()
                .withPrefix(":")
                .withUpperCase()
                .formatHex(hash);

        return HASH_ALGORITHM + hex;
    }
}
