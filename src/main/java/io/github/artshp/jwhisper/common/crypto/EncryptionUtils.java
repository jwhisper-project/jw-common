package io.github.artshp.jwhisper.common.crypto;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;

@Slf4j
public class EncryptionUtils {

    public static byte[] encrypt(PublicKey publicKey, byte[] data) {
        Cipher cipher = SecurityUtils.newCipher();

        try {
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            return cipher.doFinal(data);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException("Unexpected error during data encryption.", e);
        }
    }

    public static byte[] decrypt(PrivateKey privateKey, byte[] encryptedData) {
        Cipher cipher = SecurityUtils.newCipher();

        try {
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            return cipher.doFinal(encryptedData);
        } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new IllegalStateException("Unexpected error during data decryption.", e);
        }
    }
}
