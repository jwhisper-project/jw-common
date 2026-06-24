package io.github.artshp.jwhisper.common.crypto;

import org.bouncycastle.jcajce.provider.asymmetric.edec.BCEdDSAPublicKey;
import org.bouncycastle.jcajce.provider.asymmetric.edec.BCXDHPublicKey;

import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;

/**
 * Public key utils. Provides encoding/decoding methods for public keys (signing and encryption).
 * Uses 32-bit encoding (only key data, no metadata regarding key type).
 */
public final class PublicKeyUtils {

    /**
     * Constructor to prohibit instantiating.
     */
    private PublicKeyUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Extracts exactly 32 raw bytes from an {@value SecurityUtils#SIGNING_ALGORITHM} or
     * {@value SecurityUtils#ENCRYPTION_ALGORITHM} public key,
     * stripping out the 12-byte X.509 ASN.1 header metadata using native BC getters.
     *
     * @param publicKey public key
     * @return 32-byte array holding pure key coordinates
     * @throws IllegalArgumentException if public key is {@code null} or neither
     * {@value SecurityUtils#SIGNING_ALGORITHM} nor {@value SecurityUtils#ENCRYPTION_ALGORITHM}
     */
    public static byte[] toRawBytes(PublicKey publicKey) throws IllegalArgumentException {
        return switch (publicKey) {
            case null -> throw new IllegalArgumentException("Public key reference cannot be null");

            // Ed25519 / Signing
            case BCEdDSAPublicKey bcKey -> bcKey.getPointEncoding();

            // X25519 / Encryption
            case BCXDHPublicKey bcKey -> bcKey.getUEncoding();

            default -> throw new IllegalArgumentException("Unsupported key type: " + publicKey.getClass().getName()
                    + ". Ensure BouncyCastle Security Provider is properly registered.");
        };
    }

    /**
     * Reconstructs a full {@value SecurityUtils#SIGNING_ALGORITHM} key from its 32 raw coordinate bytes.
     *
     * @param raw32Bytes 32 bytes of the public key coordinates
     * @return reconstructed public key
     * @throws InvalidKeySpecException if the given key specification is inappropriate
     * for this key factory to produce a public key
     */
    public static PublicKey newSigningPublicKey(byte[] raw32Bytes) throws InvalidKeySpecException {
        if (raw32Bytes == null || raw32Bytes.length != 32) {
            throw new IllegalArgumentException("Raw %s key array must be exactly 32 bytes long"
                    .formatted(SecurityUtils.SIGNING_ALGORITHM));
        }

        // Formulate the structural 12-byte X.509 ASN.1 header for Ed25519 manually
        byte[] x509Header = {
                0x30, 0x2a,                    // Sequence block
                0x30, 0x05,                    // Algorithm Identifier Sequence
                0x06, 0x03, 0x2b, 0x65, 0x70,  // OID: 1.3.101.112 (Ed25519)
                0x03, 0x21, 0x00               // Bit String header mapping configuration
        };

        byte[] encodedKey = new byte[x509Header.length + 32];
        System.arraycopy(x509Header, 0, encodedKey, 0, x509Header.length);
        System.arraycopy(raw32Bytes, 0, encodedKey, x509Header.length, 32);

        return SecurityUtils.newSigningPublicKey(encodedKey);
    }

    /**
     * Reconstructs a full {@value SecurityUtils#ENCRYPTION_ALGORITHM} key from its 32 raw coordinate bytes.
     *
     * @param raw32Bytes 32 bytes of the public key coordinates
     * @return reconstructed public key
     * @throws InvalidKeySpecException if the given key specification is inappropriate
     * for this key factory to produce a public key
     */
    public static PublicKey newEncryptionPublicKey(byte[] raw32Bytes) throws InvalidKeySpecException {
        if (raw32Bytes == null || raw32Bytes.length != 32) {
            throw new IllegalArgumentException("Raw %s key array must be exactly 32 bytes long"
                    .formatted(SecurityUtils.ENCRYPTION_ALGORITHM));
        }

        // Formulate the structural 12-byte X.509 ASN.1 header for X25519 manually
        byte[] x509Header = {
                0x30, 0x2a,                    // Sequence block
                0x30, 0x05,                    // Algorithm Identifier Sequence
                0x06, 0x03, 0x2b, 0x65, 0x6e,  // OID: 1.3.101.110 (X25519)
                0x03, 0x21, 0x00               // Bit String header mapping configuration
        };

        byte[] encodedKey = new byte[x509Header.length + 32];
        System.arraycopy(x509Header, 0, encodedKey, 0, x509Header.length);
        System.arraycopy(raw32Bytes, 0, encodedKey, x509Header.length, 32);

        return SecurityUtils.newEncryptionPublicKey(encodedKey);
    }
}
