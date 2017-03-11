package com.michaelssss;

import com.michaelssss.encryptor.EncryptorFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Objects;

public class AnnotationHandlerImpl implements AnnotationHandler {
    private final static Logger logger = LogManager.getLogger(AnnotationHandler.class);
    private EncryptorFactory factory;

    AnnotationHandlerImpl() {
        factory = new EncryptorFactory();
    }

    @Override
    public void handle(Object e)
            throws NoSupportEncryptTypeException {
        Field[] fields = e.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (hasEncryptionAnnotation(field)) {
                handleEncryption(e, field);
            }
        }
    }

    private void handleEncryption(Object e, Field field) throws NoSupportEncryptTypeException {
        if (!field.getType().equals(String.class)) {
            throw new NoSupportEncryptTypeException();
        }
        Encryption encryption = field.getAnnotation(Encryption.class);
        field.setAccessible(true);
        try {
            String s = (String) field.get(e);

            field.set(e,
                    bytesToHexString(StringUtils.isNotBlank(encryption.key()) ? factory.getEncrytor(encryption.encryptor()).encrypt(s.getBytes("UTF-8"), encryption.key().getBytes("UTF-8")) : factory.getEncrytor(encryption.encryptor()).encrypt(s.getBytes("UTF-8"))));
        } catch (Exception ex) {
            logger.error(ex);
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
