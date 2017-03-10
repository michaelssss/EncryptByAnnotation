package com.michaelssss;

import com.michaelssss.encryptor.EncryptorFactory;

import java.lang.reflect.Field;

public class AnnotationHandlerImpl implements AnnotationHandler {
    private EncryptorFactory factory;

    AnnotationHandlerImpl() {
        factory = new EncryptorFactory();
    }

    @Override
    public void handleEncryption(Object e)
            throws NoSupportEncryptTypeException {
        Field[] fields = e.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (hasEncryptionAnnotation(field)) {
                if (!field.getType().equals(String.class)) {
                    throw new NoSupportEncryptTypeException();
                }
                Encryption encryption = field.getAnnotation(Encryption.class);
                field.setAccessible(true);
                try {
                    String s = (String) field.get(e);
                    field.set(e,
                            bytesToHexString(factory.getEncrytor(encryption.encryptor()).encrypt(s.getBytes("UTF-8"), encryption.key().getBytes("UTF-8"))));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
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
        return null != field.getAnnotation(Encryption.class);
    }
}
