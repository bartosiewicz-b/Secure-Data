package com.secureData.services;

import com.secureData.services.interf.CryptoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

class CryptoServiceImplTest {

    char[] message;
    char[] password;
    char[] salt;

    CryptoService cryptoService;

    @BeforeEach
    void setUp() {
        message = "Hello World!".toCharArray();
        password = "password".toCharArray();
        salt = "salt".toCharArray();

        cryptoService = new CryptoServiceImpl();
    }

    @Test
    void testEncryptionDecryption() throws InvalidAlgorithmParameterException, NoSuchPaddingException,
            IllegalBlockSizeException, NoSuchAlgorithmException, BadPaddingException, InvalidKeySpecException,
            InvalidKeyException {

        byte[] encrypted = cryptoService.encrypt(message, password);
        char[] decrypted = cryptoService.decrypt(encrypted, password);

        assertArrayEquals(message, decrypted);
    }

    @Test
    void testPasswordComparison() {
        char[] pass2 = "pass".toCharArray();

        assertTrue(cryptoService.comparePasswords(password, password));
        assertFalse(cryptoService.comparePasswords(password, pass2));
    }
}