package com.secureData.services.interf;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface CryptoService {

    byte[] encrypt(char[] data, char[] password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException;

    char[] decrypt(byte[] data, char[] password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException;
}
