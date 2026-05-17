package io.github.artshp.jwhisper.common.crypto;

import lombok.extern.slf4j.Slf4j;

import java.security.*;

/**
 * Signing utils.
 */
@Slf4j
public final class SigningUtils {

    /**
     * Constructor to prohibit instantiating.
     */
    private SigningUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Sign data using private key.
     * @param privateKey private key
     * @param data data to sign
     * @return data signature
     */
    public static byte[] sign(PrivateKey privateKey, byte[] data) {
        Signature sig = SecurityUtils.newSignature();

        try {
            sig.initSign(privateKey);
            sig.update(data);

            return sig.sign();
        } catch (InvalidKeyException | SignatureException e) {
            throw new IllegalStateException("Unexpected error during signing data.", e);
        }
    }

    /**
     * Verify data signature using public key.
     * @param publicKey public key
     * @param data data to verify
     * @param signature data signature
     * @return {@code true} if signature is valid, otherwise {@code false}
     */
    public static boolean verify(PublicKey publicKey, byte[] data, byte[] signature) {
        Signature sig = SecurityUtils.newSignature();

        try {
            sig.initVerify(publicKey);
            sig.update(data);

            return sig.verify(signature);
        } catch (InvalidKeyException | SignatureException e) {
            LOGGER.warn("Unexpected error during verifying signature.", e);
            return false;
        }
    }
}
