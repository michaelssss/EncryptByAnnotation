package com.michaelssss.encryptor;

import org.junit.Assert;
import org.junit.Test;


public class TestEncryptorFactory {
    @Test
    public void testCheckLoadPropertiesSucceed() {
        EncryptorFactory factory = new EncryptorFactory();
        try {
            Assert.assertNotNull(factory.getEncrytor(ECBEncryptor.class.getSimpleName()));
        } catch (Exception e) {
            Assert.fail();
        }
    }
}
