package com.michaelssss.encryptor;

import com.michaelssss.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;

class ECBEncryptor implements Encryptor {
    private final static Logger logger = LogManager.getLogger(Encryptor.class);

    ECBEncryptor() {
        logger.info("ECBEncryptor successful");
    }

    @Override
    public byte[] encrypt(byte[] plainObject) {
        return ECBEncryptor(plainObject, Keys.ECBKEY.getKey().getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public byte[] encrypt(byte[] plainObject, byte[] key) {
        return ECBEncryptor(plainObject, key);
    }

    private byte[] ECBEncryptor(byte[] plainData, byte[] key) {
        byte[] data = new byte[0];
        try {
            Cipher ecbCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] keys = new byte[16];
            System.arraycopy(key, 0, keys, 0, key.length > keys.length ? keys.length : key.length);
            SecretKey secretKey = new SecretKeySpec(keys, "AES");
            ecbCipher.init(Cipher.ENCRYPT_MODE, secretKey, new SecureRandom());
            data = ecbCipher.doFinal(plainData);
        } catch (Exception e) {
            logger.error("something horrible happend ", e);
        }
        return data;
    }
}
