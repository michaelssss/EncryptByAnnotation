package com.michaelssss;

import java.lang.reflect.Field;

public class AnnotationHandlerImpl implements AnnotationHandler
{
    private Encryptor ECBEncryptor;

    public AnnotationHandlerImpl()
    {
        EncryptorFactory factory = new EncryptorFactory();
        try
        {
            ECBEncryptor = factory.getEncrytor(ECBEncryptor.class.getSimpleName());
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        assert ECBEncryptor != null;
    }

    @Override
    public void handleECB(Object e)
        throws NoSupportEncryptTypeException
    {
        Field[] fields = e.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            if (hasECBAnnotation(field))
            {
                if (!field.getType().equals(String.class))
                {
                    throw new NoSupportEncryptTypeException();
                }
                ECB ecb = field.getAnnotation(ECB.class);
                field.setAccessible(true);
                try
                {
                    String s = (String)field.get(e);
                    field.set(e,
                        bytesToHexString(ECBEncryptor.encrypt(s.getBytes("UTF-8"), ecb.key().getBytes("UTF-8"))));
                }
                catch (Exception ex)
                {
                    ex.printStackTrace();
                }
            }
        }
    }

    private String bytesToHexString(byte[] src)
    {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0)
        {
            return null;
        }
        for (int i = 0; i < src.length; i++)
        {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2)
            {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv + " ");
        }
        return stringBuilder.toString();
    }

    private boolean hasECBAnnotation(Field field)
    {
        return null != field.getAnnotation(ECB.class);
    }
}
