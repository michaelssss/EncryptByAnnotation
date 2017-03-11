package com.michaelssss.decryptor;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by michaelssss on 2017/3/11.
 */
public class TestDecryptorFactory {
    @Test
    public void testGetDecrytor() {
        DecryptorFactory factory = new DecryptorFactory();
        try {
            Assert.assertNotNull(factory.getDecrytor(ECBDecryptor.class.getSimpleName()));
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }
}
