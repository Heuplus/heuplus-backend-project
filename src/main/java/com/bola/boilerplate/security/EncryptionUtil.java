package com.bola.boilerplate.security;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Component
public class EncryptionUtil {
    // This is a secret key that should be stored in a secure location
    // and should not be shared with anyone.
    // We will switch to the AWS KMS service to manage the keys in the future.
    // This key is used to encrypt and decrypt the data.
    private final String key = "882BFCBF6921A";
    private final String initVector = "DC3F7399812CA";
    private final String algorithm = "AES/CBC/PKCS5PADDING";

    public String encrypt(String value) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE,
                    new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"),
                    new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8)));
            byte[] encrypted = cipher.doFinal(value.getBytes());
            return Base64.encodeBase64String(encrypted);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public String decrypt(String encrypted) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE,
                    new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES"),
                    new IvParameterSpec(initVector.getBytes(StandardCharsets.UTF_8)));
            byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}