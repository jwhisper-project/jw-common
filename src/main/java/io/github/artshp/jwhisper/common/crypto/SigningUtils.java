package io.github.artshp.jwhisper.common.crypto;

import lombok.extern.slf4j.Slf4j;

import java.security.*;

@Slf4j
public class SigningUtils {

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

    public static boolean verify(PublicKey publicKey, byte[] data, byte[] signature) {
        Signature sig = SecurityUtils.newSignature();

        try {
            sig.initVerify(publicKey);
            sig.update(data);

            return sig.verify(signature);
        } catch (InvalidKeyException | SignatureException e) {
            log.warn("Unexpected error during verifying signature.", e);
            return false;
        }
    }
}
