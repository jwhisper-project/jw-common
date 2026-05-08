package io.github.artshp.jwhisper.common.crypto;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;

public class SigningUtils {

    public static byte[] sign(PrivateKey privateKey, byte[] data) throws Exception {
        Signature sig = SecurityUtils.newSignature();

        sig.initSign(privateKey);
        sig.update(data);

        return sig.sign();
    }

    public static boolean verify(PublicKey publicKey, byte[] data, byte[] signature) throws Exception {
        Signature sig = SecurityUtils.newSignature();

        sig.initVerify(publicKey);
        sig.update(data);

        return sig.verify(signature);
    }
}
