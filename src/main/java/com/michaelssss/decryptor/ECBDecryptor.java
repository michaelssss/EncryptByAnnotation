package com.michaelssss.decryptor;

import com.michaelssss.Keys;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.Charset;
import java.security.SecureRandom;

/**
 * Created by michaelssss on 2017/3/11.
 */
class ECBDecryptor implements Decryptor {
    private final static Logger logger = LogManager.getLogger(Decryptor.class);

    ECBDecryptor() {
        logger.info("ECBDecryptor initial successful");
    }

    @Override
    public byte[] decrypt(byte[] plainObject) {
        return ECBDecryptor(plainObject, Keys.ECBKEY.getKey().getBytes(Charset.forName("UTF-8")));
    }

    @Override
    public byte[] decrypt(byte[] plainObject, byte[] key) {
        return ECBDecryptor(plainObject, key);
    }

    private byte[] ECBDecryptor(byte[] plainData, byte[] key) {
        byte[] data = new byte[0];
        try {
            Cipher ecbCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            byte[] keys = new byte[16];
            System.arraycopy(key, 0, keys, 0, key.length > keys.length ? keys.length : key.length);
            SecretKey secretKey = new SecretKeySpec(keys, "AES");
            ecbCipher.init(Cipher.DECRYPT_MODE, secretKey, new SecureRandom());
            data = ecbCipher.doFinal(plainData);
        } catch (Exception e) {
            logger.error("something horrible happend ", e);
        }
        return data;
    }
}
