package com.michaelssss;

import org.junit.Test;

public class TestECB {
    @Test
    public void testAnnotationHandler() {
        TestClass testClass = new TestClass("aaa", "bbb");
        AnnotationHandler handler = new AnnotationHandlerImpl();
        try {
            System.out.println("pre " + testClass);
            handler.handleEncryption(testClass);
            System.out.println("after " + testClass);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static class TestClass {
        @Encryption(encryptor = "ECBEncryptor", key = "heiheiheiheiheiheiheiheihei")
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
