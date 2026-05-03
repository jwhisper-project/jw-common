package io.github.artshp.jwhisper.common.crypto;

import io.github.artshp.jwhisper.common.exception.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.net.ssl.KeyManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.util.Optional;

@Slf4j
public class SecurityUtils {

    public static final String CERTIFICATE_TYPE = "X.509";
    public static final String KEY_STORE_TYPE = "PKCS12";

    public static final String HASH_ALGORITHM = "SHA-256";
    public static final String USER_KEYS_ALGORITHM = "Ed25519";

    public static final String SSL_PROTOCOL = "TLSv1.3";

    public static final Provider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    public static final CertificateFactory CERTIFICATE_FACTORY;
    public static final MessageDigest MESSAGE_DIGEST;
    public static final KeyPairGenerator KEY_PAIR_GENERATOR;

    static {
        Security.addProvider(BOUNCY_CASTLE_PROVIDER);

        try {
            CERTIFICATE_FACTORY = CertificateFactory.getInstance(CERTIFICATE_TYPE);
        } catch (CertificateException e) {
            throw new IllegalStateException(CERTIFICATE_TYPE + " certificate type is not supported.", e);
        }

        try {
            MESSAGE_DIGEST = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(HASH_ALGORITHM + " hash algorithm is not supported.", e);
        }

        try {
            KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance(USER_KEYS_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(USER_KEYS_ALGORITHM + " key gen algorithm is not supported.", e);
        }
    }

    public static KeyStore newKeyStore() {
        try {
            return KeyStore.getInstance(KEY_STORE_TYPE);
        } catch (KeyStoreException e) {
            throw new IllegalStateException(KEY_STORE_TYPE + " key store type is not supported.", e);
        }
    }

    public static KeyManagerFactory newKeyManagerFactory() {
        final String algorithm = KeyManagerFactory.getDefaultAlgorithm();
        try {
            return KeyManagerFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(algorithm + " key manager factory algorithm is not supported.", e);
        }
    }

    public static void loadKeyStore(KeyStore keyStore, char[] password, Path path) {
        try (InputStream fis = Files.newInputStream(path)) {
            keyStore.load(fis, password);
        } catch (NoSuchAlgorithmException | CertificateException e) {
            log.error("Failed to load key store from file \"{}\".", path, e);
            throw new RuntimeException("Failed to load key store", e);
        } catch (IOException e) {
            Class<?> causeClass = Optional.ofNullable(e.getCause())
                    .map(Throwable::getClass)
                    .orElse(null);

            // Was it caused by wrong password?
            if (UnrecoverableKeyException.class.equals(causeClass)) {
                throw new WrongPasswordException("Wrong password provided for key store", e);
            } else {
                log.error("Failed to load key store from file \"{}\".", path, e);
                throw new RuntimeException("Failed to load key store", e);
            }
        }
    }

    public static KeyStore createAndLoadEmptyKeyStore() {
        KeyStore keyStore = newKeyStore();
        try {
            keyStore.load(null, null);
            return keyStore;
        } catch (IOException | NoSuchAlgorithmException | CertificateException e) {
            log.error("Failed to initialize empty key store.", e);
            throw new RuntimeException("Failed to initialize empty key store", e);
        }
    }

    public static void saveKeyStore(KeyStore keyStore, char[] password, Path path) {
        try (OutputStream fos = Files.newOutputStream(path)) {
            keyStore.store(fos, password);
        } catch (KeyStoreException e) {
            throw new IllegalArgumentException("Key store has not been initialized", e);
        } catch (NoSuchAlgorithmException | CertificateException | IOException e) {
            log.error("Failed to save key store to file \"{}\".", path, e);
            throw new RuntimeException("Failed to save key store", e);
        }
    }
}
