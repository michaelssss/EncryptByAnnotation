package com.michaelssss;

import org.junit.Test;

public class TestECB {
    @Test
    public void testAnnotationHandler() {
        TestClass testClass = new TestClass("aaa", "bbb");
        AnnotationHandler encryptionAnnotationHandler = new EncryptionAnnotationHandlerImpl();
        AnnotationHandler decryptionAnnotationHandler = new DecryptionAnnotationHandlerImpl();
        try {
            System.out.println("pre " + testClass);
            encryptionAnnotationHandler.handle(testClass);
            System.out.println("after " + testClass);
            decryptionAnnotationHandler.handle(testClass);
            System.out.println("after1 " + testClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static class TestClass {

        @Encryption(encryptor = "ECBEncryptor")
        @Decryption(decryptor = "ECBDecryptor")
        String abc;

        String ccc;

        TestClass(String abc, String ccc) {
            this.abc = abc;
            this.ccc = ccc;
        }

        @Override
        public String toString() {
            return "TestClass{" +
                    "abc='" + abc + '\'' +
                    ", ccc='" + ccc + '\'' +
                    '}';
        }
    }
}
