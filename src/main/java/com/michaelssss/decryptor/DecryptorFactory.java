package com.michaelssss.decryptor;

import com.michaelssss.NoDeclearEncryptMethodException;
import com.michaelssss.encryptor.EncryptorFactory;
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

/**
 * Created by michaelssss on 2017/3/11.
 */
public class DecryptorFactory {
    private final static Logger logger = LogManager.getLogger(DecryptorFactory.class);
    private static final Map<String, Decryptor> decryptorMap = new TreeMap<>();

    static {
        Properties properties = new Properties();
        URL path = EncryptorFactory.class.getClassLoader().getResource("decryptorList.properties");
        try (InputStream in = new FileInputStream(path.getPath())) {
            properties.load(in);
        } catch (IOException e) {
            logger.fatal(e);
        }
        for (Map.Entry entry : properties.entrySet()) {
            try {
                Object o = Class.forName(entry.getValue().toString()).newInstance();
                if (o instanceof Decryptor) {
                    registerDecryptor(entry.getKey().toString(), (Decryptor) o);
                }
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                logger.fatal(e);
            }
        }
        logger.info("Decryptor Initial successful");
        logger.info("load {} Decryptor", decryptorMap.size());
    }

    private static void registerDecryptor(String name, Decryptor decryptor) {
        decryptorMap.put(name, decryptor);
    }

    public Decryptor getDecrytor(String encryWay)
            throws NoDeclearEncryptMethodException {
        Decryptor encryptor = decryptorMap.get(encryWay);
        if (Objects.nonNull(encryptor)) {
            return encryptor;
        }
        throw new NoDeclearEncryptMethodException();
    }
}
