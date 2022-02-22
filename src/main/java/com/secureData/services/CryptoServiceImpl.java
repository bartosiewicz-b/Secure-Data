package com.secureData.services;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import com.secureData.services.interf.CryptoService;

public class CryptoServiceImpl implements CryptoService {

    public byte[] encrypt(char[] message, char[] password, char[] salt) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{

        SecretKey key = generateKey(password, salt);
        IvParameterSpec iv = generateIV();

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encrypted = cipher.doFinal(charsToBytes(message));

        byte[] res = new byte[16 + encrypted.length];
        System.arraycopy(iv.getIV(), 0, res, 0, 16);
        System.arraycopy(encrypted, 0, res, 16, encrypted.length);

        return res;
    }

    public char[] decrypt(byte[] message, char[] password, char[] salt) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {

        SecretKey key = generateKey(password, salt);
        IvParameterSpec iv = new IvParameterSpec(Arrays.copyOf(message, 16));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        return bytesToChars(cipher.doFinal(Arrays.copyOfRange(message, 16, message.length)));
    }

    private static SecretKey generateKey(char[] password, char[] salt) throws
            NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, charsToBytes(salt), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private static IvParameterSpec generateIV() {
        byte[] ivBytes = new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        return new IvParameterSpec(ivBytes);
    }

    private static byte[] charsToBytes(char[] msg) {
        byte[] res = new byte[msg.length];
        for(int i = 0; i < msg.length; i++)
            res[i] = (byte)msg[i];

        return res;
    }

    private static char[] bytesToChars(byte[] msg) {
        char[] res = new char[msg.length];
        for(int i = 0; i < msg.length; i++)
            res[i] = (char)msg[i];

        return res;
    }
}
