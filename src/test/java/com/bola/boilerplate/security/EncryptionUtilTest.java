package com.bola.boilerplate.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class EncryptionUtilTest {

  @Autowired private EncryptionUtil encryptionUtil;

  private String plainText;
  private String encryptedText;

  @BeforeEach
  public void setUp() {
    plainText = "Heuplus is the best!";
    encryptedText = encryptionUtil.encrypt(plainText);
  }

  @Test
  void encryptReturnsNonNullValue() {
    assertNotNull(encryptedText);
  }

  @Test
  void encryptReturnsDifferentValueThanInput() {
    assertNotEquals(plainText, encryptedText);
  }

  @Test
  void decryptReturnsOriginalValue() {
    String decryptedText = encryptionUtil.decrypt(encryptedText);
    assertEquals(plainText, decryptedText);
  }

  @Test
  void decryptWithInvalidInputReturnsNull() {
    String decryptedText = encryptionUtil.decrypt("InvalidEncryptedText");
    assertNull(decryptedText);
  }

  @Test
  void encryptAndDecryptWithEmptyString() {
    String emptyString = "";
    String encryptedEmptyString = encryptionUtil.encrypt(emptyString);
    String decryptedEmptyString = encryptionUtil.decrypt(encryptedEmptyString);
    assertEquals(emptyString, decryptedEmptyString);
  }

  @Test
  void encryptAndDecryptWithNull() {
    String encryptedNull = encryptionUtil.encrypt(null);
    String decryptedNull = encryptionUtil.decrypt(encryptedNull);
    assertNull(decryptedNull);
  }
}
