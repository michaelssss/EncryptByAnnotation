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
                handleDecryption(e, field);
            }
        }
    }

    private boolean hasDecryptionAnnotation(Field field) {
        return Objects.nonNull(field.getAnnotation(Decryption.class));
    }

    private void handleDecryption(Object e, Field field) throws NoSupportEncryptTypeException {
        if (!field.getType().equals(String.class)) {
            throw new NoSupportEncryptTypeException();
        }
        Decryption decryption = field.getAnnotation(Decryption.class);
        field.setAccessible(true);
        try {
            Decryptor decryptor = factory.getDecrytor(decryption.decryptor());
            String s = (String) field.get(e);
            field.set(e,
                    new String(StringUtils.isNotBlank(decryption.key()) ? decryptor.decrypt(toByte(s), decryption.key().getBytes(Charset.forName("UTF-8"))) : decryptor.decrypt(toByte(s)), Charset.forName("UTF-8")));
        } catch (Exception ex) {
            logger.error("", ex);
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
