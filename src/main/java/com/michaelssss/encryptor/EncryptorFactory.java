package com.michaelssss.encryptor;

import com.michaelssss.NoDeclearEncryptMethodException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeMap;

public class EncryptorFactory
{
    private static final Map<String, Encryptor> encryptorMap = new TreeMap<>();

    static
    {
        Properties properties = new Properties();
        URL path = EncryptorFactory.class.getClassLoader().getResource("encryptorList.properties");
        try (InputStream in = new FileInputStream(path.getPath()))
        {
            properties.load(in);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        for (Map.Entry entry : properties.entrySet())
        {
            try
            {
                Object o = Class.forName(entry.getValue().toString()).newInstance();
                if (o instanceof Encryptor)
                {
                    registerEncryptor(entry.getKey().toString(), (Encryptor)o);
                }
            }
            catch (ClassNotFoundException | IllegalAccessException | InstantiationException e)
            {
                e.printStackTrace();
            }

        }
    }

    private static void registerEncryptor(String name, Encryptor encryptor) {
        encryptorMap.put(name, encryptor);
    }

    public Encryptor getEncrytor(String encryWay)
        throws NoDeclearEncryptMethodException
    {
        Encryptor encryptor = encryptorMap.get(encryWay);
        if (Objects.nonNull(encryptor))
        {
            return encryptor;
        }
        throw new NoDeclearEncryptMethodException();
    }
}
