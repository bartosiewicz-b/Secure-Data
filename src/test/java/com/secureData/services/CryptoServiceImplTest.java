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

        byte[] encrypted = cryptoService.encrypt(message, password, salt);
        char[] decrypted = cryptoService.decrypt(encrypted, password, salt);

        assertArrayEquals(message, decrypted);
    }
}