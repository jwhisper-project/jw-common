package io.github.artshp.jwhisper.common.crypto;

import io.github.artshp.jwhisper.common.exception.WrongPasswordException;
import lombok.extern.slf4j.Slf4j;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Optional;

/**
 * Security utils. Provides a variety of factories and generators of keys, certificates, etc.
 */
@Slf4j
public final class SecurityUtils {

    /**
     * Default certificate type.
     */
    public static final String CERTIFICATE_TYPE = "X.509";

    /**
     * Default key store type.
     */
    public static final String KEY_STORE_TYPE = "PKCS12";

    /**
     * Default hash algorithm.
     */
    public static final String HASH_ALGORITHM = "SHA-256";

    /**
     * Default messages signing algorithm.
     */
    public static final String SIGNING_ALGORITHM = "Ed25519";

    /**
     * Default messages encryption algorithm.
     */
    public static final String ENCRYPTION_ALGORITHM = "X25519";

    /**
     * Default cryptographic algorithm for messages encryption.
     */
    public static final String CRYPTO_TRANSFORMATION = "AES/GCM/NoPadding";

    /**
     * Default SSL protocol.
     */
    public static final String SSL_PROTOCOL = "TLSv1.3";

    /**
     * Bouncy Castle provider.
     * @see Provider
     */
    public static final Provider BOUNCY_CASTLE_PROVIDER = new BouncyCastleProvider();

    /**
     * Default certificate factory.
     */
    public static final CertificateFactory CERTIFICATE_FACTORY;

    /**
     * Default message digest (fingerprints).
     */
    public static final MessageDigest MESSAGE_DIGEST;

    /**
     * Default key pair generator of signing keys.
     */
    public static final KeyPairGenerator SIGNING_KEY_PAIR_GENERATOR;

    /**
     * Default key pair generator of encryption keys.
     */
    public static final KeyPairGenerator ENCRYPTION_KEY_PAIR_GENERATOR;

    /**
     * Default key factory of signing keys.
     */
    public static final KeyFactory SIGNING_KEY_FACTORY;

    /**
     * Default key factory of encryption keys.
     */
    public static final KeyFactory ENCRYPTION_KEY_FACTORY;

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
            SIGNING_KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance(SIGNING_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(SIGNING_ALGORITHM + " key gen algorithm is not supported.", e);
        }

        try {
            ENCRYPTION_KEY_PAIR_GENERATOR = KeyPairGenerator.getInstance(ENCRYPTION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(ENCRYPTION_ALGORITHM + " key gen algorithm is not supported.", e);
        }

        try {
            SIGNING_KEY_FACTORY = KeyFactory.getInstance(SIGNING_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(SIGNING_ALGORITHM + " key gen algorithm is not supported.", e);
        }

        try {
            ENCRYPTION_KEY_FACTORY = KeyFactory.getInstance(ENCRYPTION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(SIGNING_ALGORITHM + " key gen algorithm is not supported.", e);
        }
    }

    private SecurityUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Create a new key store.
     * @return new {@link KeyStore} instance of {@link #KEY_STORE_TYPE} type
     */
    public static KeyStore newKeyStore() {
        try {
            return KeyStore.getInstance(KEY_STORE_TYPE);
        } catch (KeyStoreException e) {
            throw new IllegalStateException(KEY_STORE_TYPE + " key store type is not supported.", e);
        }
    }

    /**
     * Create a new key manager factory.
     * @return new {@link KeyManagerFactory} instance of default type
     */
    public static KeyManagerFactory newKeyManagerFactory() {
        final String algorithm = KeyManagerFactory.getDefaultAlgorithm();
        try {
            return KeyManagerFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(algorithm + " key manager factory algorithm is not supported.", e);
        }
    }

    /**
     * Create a new trust manager factory.
     * @return new {@link TrustManagerFactory} instance of default type
     */
    public static TrustManagerFactory newTrustManagerFactory() {
        final String algorithm = TrustManagerFactory.getDefaultAlgorithm();
        try {
            return TrustManagerFactory.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(algorithm + " trust manager factory algorithm is not supported.", e);
        }
    }

    /**
     * Load key store.
     * @param keyStore key store instance to load
     * @param password password to key store
     * @param path path to key store
     * @throws WrongPasswordException if provided password is wrong
     */
    public static void loadKeyStore(KeyStore keyStore, char[] password, Path path)
            throws WrongPasswordException {
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

    /**
     * @return new loaded empty key store
     */
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

    /**
     * Create a new key store and load it.
     * @param password password to key store
     * @param path path to key store
     * @return a new loaded key store
     */
    public static KeyStore createAndLoadKeyStore(char[] password, Path path) {
        KeyStore keyStore = newKeyStore();
        loadKeyStore(keyStore, password, path);
        return keyStore;
    }

    /**
     * Persist key store.
     * @param keyStore key store to persist
     * @param password password to key store
     * @param path path where to store
     */
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

    /**
     * @return a new {@link Signature} instance of {@link #SIGNING_ALGORITHM} type
     */
    public static Signature newSignature() {
        try {
            return Signature.getInstance(SIGNING_ALGORITHM, BOUNCY_CASTLE_PROVIDER);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(SIGNING_ALGORITHM + " signature algorithm is not supported.", e);
        }
    }

    /**
     * Create a new signing key instance using given bytes
     * @param publicKey public key bytes
     * @return a new signing key
     * @throws InvalidKeySpecException if given key specification is inappropriate
     */
    public static PublicKey newSigningPublicKey(byte[] publicKey) throws InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        return SIGNING_KEY_FACTORY.generatePublic(keySpec);
    }

    /**
     * Create a new encryption key instance using given bytes
     * @param publicKey public key bytes
     * @return a new encryption key
     * @throws InvalidKeySpecException if given key specification is inappropriate
     */
    public static PublicKey newEncryptionPublicKey(byte[] publicKey) throws InvalidKeySpecException {
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
        return ENCRYPTION_KEY_FACTORY.generatePublic(keySpec);
    }

    /**
     * @return new {@link Cipher} instance of {@link #CRYPTO_TRANSFORMATION} type
     */
    public static Cipher newCipher() {
        try {
            return Cipher.getInstance(CRYPTO_TRANSFORMATION);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new IllegalStateException(CRYPTO_TRANSFORMATION + " cipher algorithm is not supported.", e);
        }
    }
}
