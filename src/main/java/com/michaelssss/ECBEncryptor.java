package com.michaelssss;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;

public class ECBEncryptor implements Encryptor
{
    @Override
    public byte[] encrypt(byte[] plainObject)
    {
        return ECBEncryptor(plainObject, "defaultKey".getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public byte[] encrypt(byte[] plainObject, byte[] key)
    {
        return ECBEncryptor(plainObject, key);
    }

    private byte[] ECBEncryptor(byte[] plainData, byte[] key)
    {
        byte[] data = new byte[0];
        try
        {
            Cipher ecbCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] keys = new byte[16];
            System.arraycopy(key, 0, keys, 0, 16);
            SecretKey secretKey = new SecretKeySpec(keys, "AES");
            ecbCipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
            data = ecbCipher.doFinal(plainData);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return data;
    }
}
