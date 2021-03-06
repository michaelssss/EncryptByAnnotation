package com.michaelssss.encryptor;

import com.michaelssss.NoDeclearEncryptMethodException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.TreeMap;

public class EncryptorFactory {
    private final static Logger logger = LogManager.getLogger(EncryptorFactory.class);
    private static final Map<String, Encryptor> encryptorMap = new TreeMap<>();

    static {
        Properties properties = new Properties();
        URL path = EncryptorFactory.class.getClassLoader().getResource("encryptorList.properties");
        try (InputStream in = new FileInputStream(path.getPath())) {
            properties.load(in);
        } catch (IOException e) {
            logger.fatal(e);
        }
        for (Map.Entry entry : properties.entrySet()) {
            try {
                Object o = Class.forName(entry.getValue().toString()).newInstance();
                if (o instanceof Encryptor) {
                    registerEncryptor(entry.getKey().toString(), (Encryptor) o);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                logger.fatal(e);
            }
        }
        logger.info("Encryptor Initial successful");
        logger.info("load {} Encryptor", encryptorMap.size());
    }

    private static void registerEncryptor(String name, Encryptor encryptor) {
        encryptorMap.put(name, encryptor);
    }

    public Encryptor getEncrytor(String encryWay)
            throws NoDeclearEncryptMethodException {
        Encryptor encryptor = encryptorMap.get(encryWay);
        if (Objects.nonNull(encryptor)) {
            return encryptor;
        }
        throw new NoDeclearEncryptMethodException();
    }
}
