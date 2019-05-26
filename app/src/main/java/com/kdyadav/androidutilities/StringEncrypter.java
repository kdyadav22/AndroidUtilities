package com.kdyadav.androidutilities;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;


/**
 * Utility Program for encrypting and decrypting strings
 */
public class StringEncrypter {
    // static Logger logger = Logger.getLogger(StringEncrypter.class.getName());
    /**
     * DESede encryption scheme
     */
    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";

    /**
     * DES encryption scheme
     */
    private static final String DES_ENCRYPTION_SCHEME = "DES";

    private static final String DEFAULT_ENCRYPTION_KEY = "This is a fairly long phrase used to encrypt";

    private static final String UNICODE_FORMAT = "UTF8";

    private KeySpec keySpec;

    private SecretKeyFactory keyFactory;

    private static final String DEFAULT_ENCRYPTION_KEY1 = "peGas@#235X798c5TEV23*9peGaT@#234X758c5TEV23*9";

    private Cipher cipher;

    public StringEncrypter() throws Exception {
        this(DES_ENCRYPTION_SCHEME, DEFAULT_ENCRYPTION_KEY1);
    }


    public StringEncrypter(String encryptionScheme) throws Exception {
        this(encryptionScheme, DEFAULT_ENCRYPTION_KEY);
        // logger.debug("in constructor");
    }

    /**
     * Constructor that uses the provided encryption key
     *
     * @param encryptionScheme the encryption scheme to be used. Pass one of the encryption
     *                         schemes defined in this class
     * @param encryptionKey    encryption key to be used. While decrypting a string, the same
     *                         key used for encryption must be used
     */
    private StringEncrypter(String encryptionScheme, String encryptionKey) throws Exception {
        // logger.debug("in constructor");
        if (encryptionKey == null) {
            // logger.error("encryption key was null");
            throw new Exception(new IllegalArgumentException("Encryption key was null"));
        }

        if (encryptionKey.trim().length() < 24) {
            throw new Exception(new IllegalArgumentException("Encryption key was less than 24 characters"));
        }

        try {
            byte[] keyAsBytes = encryptionKey.getBytes(UNICODE_FORMAT);

            switch (encryptionScheme) {
                case DESEDE_ENCRYPTION_SCHEME:
                    keySpec = new DESedeKeySpec(keyAsBytes);
                    break;
                case DES_ENCRYPTION_SCHEME:
                    keySpec = new DESKeySpec(keyAsBytes);
                    break;
                default:
                    throw new Exception(new IllegalArgumentException("Encryption scheme not supported: " + encryptionScheme));
            }

            keyFactory = SecretKeyFactory.getInstance(encryptionScheme);
            cipher = Cipher.getInstance(encryptionScheme);
        } catch (InvalidKeyException | UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException e) {
            throw new Exception(e);
        }
    }

    /**
     * Encrypt a string
     *
     * @param unencryptedString the string to be encrypted
     * @return the encrypted string
     */
    public String encrypt(String unencryptedString) throws Exception {
        if ((unencryptedString == null) || (unencryptedString.trim().length() == 0)) {
            throw new Exception(new IllegalArgumentException("unencrypted string was null or empty"));
        }

        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.ENCRYPT_MODE, key);

            byte[] cleartext = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] ciphertext = cipher.doFinal(cleartext);

            BASE64Encoder base64encoder = new BASE64Encoder();

            return base64encoder.encode(ciphertext);
        } catch (InvalidKeyException | InvalidKeySpecException | IllegalStateException | UnsupportedEncodingException | BadPaddingException | IllegalBlockSizeException e) {
            throw new Exception(e);
        }
    }

    /**
     * Decrypt a string that was encrypted by this utility earlier
     *
     * @param encryptedString the string to be decrypted
     * @return the decrypted string
     */
    public String decrypt(String encryptedString) throws Exception {
        if ((encryptedString == null) || (encryptedString.trim().length() <= 0)) {
            throw new Exception(new IllegalArgumentException("encrypted string was null or empty"));
        }

        try {
            SecretKey key = keyFactory.generateSecret(keySpec);
            cipher.init(Cipher.DECRYPT_MODE, key);

            BASE64Decoder base64decoder = new BASE64Decoder();
            byte[] cleartext = base64decoder.decodeBuffer(encryptedString);
            byte[] ciphertext = cipher.doFinal(cleartext);

            return bytes2String(ciphertext);
        } catch (InvalidKeyException | InvalidKeySpecException | IllegalBlockSizeException | IOException | BadPaddingException e) {
            throw new Exception(e);
        }
    }

    private static String bytes2String(byte[] bytes) {
        StringBuilder stringBuffer = new StringBuilder();

        for (byte aByte : bytes) {
            stringBuffer.append((char) aByte);
        }

        return stringBuffer.toString();
    }

    /**
     * Use this method for generating encrypted strings.
     *
     * @param args Provide the string to be encrypted
     */
    public String getPassword(String args) throws Exception {

        String encryptionKey = "peGas@#235X798c5TEV23*9peGaT@#234X758c5TEV23*9";
        StringEncrypter encrypter = new StringEncrypter(StringEncrypter.DES_ENCRYPTION_SCHEME, encryptionKey);
        return encrypter.decrypt(args);

    }

    public static void main(String args[]) {
        try {
            StringEncrypter enc = new StringEncrypter();
        } catch (Exception e) {
            e.getMessage();
        }
    }

}
