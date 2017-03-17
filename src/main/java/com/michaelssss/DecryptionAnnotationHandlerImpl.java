package com.michaelssss;


import com.michaelssss.decryptor.Decryptor;
import com.michaelssss.decryptor.DecryptorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Objects;

public class DecryptionAnnotationHandlerImpl implements AnnotationHandler {
    private static final DecryptorFactory factory = new DecryptorFactory();
    private static final Logger logger = LogManager.getLogger(DecryptionAnnotationHandlerImpl.class);

    @Override
    public void handle(Object e)
            throws NoSupportEncryptTypeException {
        Field[] fields = e.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (hasDecryptionAnnotation(field)) {
                if (FieldUtils.isStringType(field)) {
                    handleStringDecryption(e, field);
                } else if (FieldUtils.isBytesBuff(field)) {
                    handleBytesBuff(e, field);
                } else {
                    throw new NoSupportEncryptTypeException(field.getType().getClass().getSimpleName());
                }
            }
        }
    }

    private boolean hasDecryptionAnnotation(Field field) {
        return Objects.nonNull(field.getAnnotation(Decryption.class));
    }

    private void handleBytesBuff(Object o, Field field) {
        Decryption decryption = field.getAnnotation(Decryption.class);
        String key = decryption.key();
        byte[] key2bytes = new byte[0];
        if (StringUtils.isNotBlank(key))
            key2bytes = key.getBytes(Charset.forName("UTF-8"));
        field.setAccessible(true);
        try {
            Decryptor decryptor = factory.getDecrytor(decryption.decryptor());
            byte[] s = (byte[]) field.get(o);
            field.set(o, StringUtils.isBlank(key) ? decryptor.decrypt(s) : decryptor.decrypt(s, key2bytes));
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            field.setAccessible(false);
        }
    }

    private void handleStringDecryption(Object e, Field field) throws NoSupportEncryptTypeException {
        Decryption decryption = field.getAnnotation(Decryption.class);
        field.setAccessible(true);
        try {
            Decryptor decryptor = factory.getDecrytor(decryption.decryptor());
            String s = (String) field.get(e);
            field.set(e,
                    new String(StringUtils.isNotBlank(decryption.key()) ? decryptor.decrypt(toByte(s), decryption.key().getBytes(Charset.forName("UTF-8"))) : decryptor.decrypt(toByte(s)), Charset.forName("UTF-8")));
        } catch (Exception ex) {
            logger.error("", ex);
        } finally {
            field.setAccessible(false);
        }
    }

    private byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2), 16).byteValue();
        return result;
    }
}
