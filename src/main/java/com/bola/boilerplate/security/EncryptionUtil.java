package com.bola.boilerplate.security;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

/**
 * This class is used to encrypt and decrypt the data. The encryption algorithm used is AES-256 with
 * GCM mode.
 */
@Component
public class EncryptionUtil {
  // This is a secret key that should be stored in a secure location
  // and should not be shared with anyone.
  // We will switch to the AWS KMS service to manage the keys in the future.
  // This key is used to encrypt and decrypt the data.
  private final String key = "882BFCBF6921A882BFCBF6921A882B"; // 32 bytes for AES-256
  private final String algorithm = "AES/GCM/NoPadding";
  private final int GCM_IV_LENGTH = 12; // Recommended length for GCM IV
  private final int GCM_TAG_LENGTH = 128; // Length of authentication tag in bits

  public String encrypt(String value) {
    try {
      byte[] iv = new byte[GCM_IV_LENGTH];
      SecureRandom secureRandom = new SecureRandom();
      secureRandom.nextBytes(iv); // Generate random IV

      Cipher cipher = Cipher.getInstance(algorithm);
      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
      GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
      cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmParameterSpec);

      byte[] encrypted = cipher.doFinal(value.getBytes(StandardCharsets.UTF_8));
      byte[] encryptedWithIv = new byte[GCM_IV_LENGTH + encrypted.length];

      System.arraycopy(iv, 0, encryptedWithIv, 0, GCM_IV_LENGTH);
      System.arraycopy(encrypted, 0, encryptedWithIv, GCM_IV_LENGTH, encrypted.length);

      return Base64.encodeBase64String(encryptedWithIv);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }

  public String decrypt(String encrypted) {
    try {
      byte[] encryptedWithIv = Base64.decodeBase64(encrypted);
      byte[] iv = new byte[GCM_IV_LENGTH];
      System.arraycopy(encryptedWithIv, 0, iv, 0, GCM_IV_LENGTH);

      Cipher cipher = Cipher.getInstance(algorithm);
      SecretKeySpec keySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
      GCMParameterSpec gcmParameterSpec = new GCMParameterSpec(GCM_TAG_LENGTH, iv);
      cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmParameterSpec);

      byte[] original =
          cipher.doFinal(encryptedWithIv, GCM_IV_LENGTH, encryptedWithIv.length - GCM_IV_LENGTH);

      return new String(original, StandardCharsets.UTF_8);
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
