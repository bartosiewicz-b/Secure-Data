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

    public byte[] encrypt(char[] data, char[] password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException, InvalidKeySpecException{

        byte[] salt = randomBytes(16);

        SecretKey key = generateKey(password, salt);
        IvParameterSpec iv = new IvParameterSpec(randomBytes(16));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        byte[] encrypted = cipher.doFinal(charsToBytes(data));

        byte[] res = new byte[32 + encrypted.length];
        System.arraycopy(salt, 0, res, 0, 16);
        System.arraycopy(iv.getIV(), 0, res, 16, 16);
        System.arraycopy(encrypted, 0, res, 32, encrypted.length);

        return res;
    }

    public char[] decrypt(byte[] data, char[] password) throws NoSuchAlgorithmException,
            NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException, InvalidKeySpecException {

        byte[] salt = new byte[16];
        System.arraycopy(data, 0, salt, 0, 16);

        SecretKey key = generateKey(password, salt);

        byte[] ivBytes = new byte[16];
        System.arraycopy(data, 16, ivBytes, 0, 16);
        IvParameterSpec iv = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, iv);

        return bytesToChars(cipher.doFinal(Arrays.copyOfRange(data, 32, data.length)));
    }

    @Override
    public boolean comparePasswords(char[] pass1, char[] pass2) {
        if(pass1.length != pass2.length) return false;

        for (int i = 0; i < pass1.length; i++)
            if(pass1[i] != pass2[i]) return false;

        return true;
    }

    private static SecretKey generateKey(char[] password, byte[] salt) throws
            NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec).getEncoded(), "AES");
    }

    private static IvParameterSpec generateIV() {
        byte[] ivBytes = new byte[16];
        new SecureRandom().nextBytes(ivBytes);
        return new IvParameterSpec(ivBytes);
    }

    private static byte[] randomBytes(int length) {
        byte[] res = new byte[length];
        new SecureRandom().nextBytes(res);
        return res;
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
