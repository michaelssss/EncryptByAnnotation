package com.michaelssss;

import com.michaelssss.encryptor.Encryptor;
import com.michaelssss.encryptor.EncryptorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.Objects;

public class EncryptionAnnotationHandlerImpl implements AnnotationHandler {
    private final static Logger logger = LogManager.getLogger(AnnotationHandler.class);
    private EncryptorFactory factory;

    EncryptionAnnotationHandlerImpl() {
        factory = new EncryptorFactory();
    }

    @Override
    public void handle(Object e)
            throws NoSupportEncryptTypeException {
        Field[] fields = e.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (hasEncryptionAnnotation(field)) {
                if (FieldUtils.isStringType(field)) {
                    handleStringEncryption(e, field);
                } else if (FieldUtils.isBytesBuff(field)) {
                    handleBytesBuff(e, field);
                } else {
                    throw new NoSupportEncryptTypeException(field.getType().getClass().getSimpleName());
                }
            }
        }
    }


    private void handleBytesBuff(Object o, Field field) {
        Encryption encryption = field.getAnnotation(Encryption.class);
        String key = encryption.key();
        byte[] key2bytes = new byte[0];
        if (StringUtils.isBlank(key))
            key2bytes = key.getBytes(Charset.forName("UTF-8"));
        String encryptor = encryption.encryptor();
        try {
            Encryptor encryptor1 = factory.getEncrytor(encryptor);
            field.setAccessible(true);
            byte[] bytes = (byte[]) field.get(o);
            field.set(o, StringUtils.isBlank(key) ? encryptor1.encrypt(bytes) : encryptor1.encrypt(bytes, key2bytes));
        } catch (Exception e) {
            logger.error("", e);
        } finally {
            field.setAccessible(false);
        }
    }

    private void handleStringEncryption(Object e, Field field) {

        Encryption encryption = field.getAnnotation(Encryption.class);
        field.setAccessible(true);
        try {
            String s = (String) field.get(e);

            field.set(e,
                    bytesToHexString(StringUtils.isNotBlank(encryption.key()) ? factory.getEncrytor(encryption.encryptor()).encrypt(s.getBytes("UTF-8"), encryption.key().getBytes("UTF-8")) : factory.getEncrytor(encryption.encryptor()).encrypt(s.getBytes("UTF-8"))));
        } catch (Exception ex) {
            logger.error(ex);
        } finally {
            field.setAccessible(false);
        }
    }

    private String bytesToHexString(byte[] srcs) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (srcs == null || srcs.length <= 0) {
            return null;
        }
        for (byte src : srcs) {
            int v = src & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    private boolean hasEncryptionAnnotation(Field field) {
        return Objects.nonNull(field.getAnnotation(Encryption.class));
    }
}
