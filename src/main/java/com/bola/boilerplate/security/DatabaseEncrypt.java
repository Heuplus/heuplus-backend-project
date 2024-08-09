package com.bola.boilerplate.security;

import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is used to encrypt and decrypt data before saving it to the database. We are using the
 * AttributeConverter interface to convert the data before saving it to the database.
 */
public class DatabaseEncrypt implements AttributeConverter<String, String> {

  @Autowired EncryptionUtil encryptionUtil;

  /**
    * This method is used to encrypt the data before saving it to the database.
   * @param s - The data to be encrypted.
   * @return The encrypted data.
   */
  @Override
  public String convertToDatabaseColumn(String s) {
    return encryptionUtil.encrypt(s);
  }

  /**
   * This method is used to decrypt the data before fetching it from the database.
   * @param s - The data to be decrypted.
   * @return The decrypted data.
   */
  @Override
  public String convertToEntityAttribute(String s) {
    return encryptionUtil.decrypt(s);
  }
}
