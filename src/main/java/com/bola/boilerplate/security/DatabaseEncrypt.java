package com.bola.boilerplate.security;

import jakarta.persistence.AttributeConverter;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class is used to encrypt and decrypt data before saving it to the database. We are using the
 * AttributeConverter interface to convert the data before saving it to the database.
 */
public class DatabaseEncrypt implements AttributeConverter<String, String> {

  @Autowired EncryptionUtil encryptionUtil;

  @Override
  public String convertToDatabaseColumn(String s) {
    return encryptionUtil.encrypt(s);
  }

  @Override
  public String convertToEntityAttribute(String s) {
    return encryptionUtil.decrypt(s);
  }
}
