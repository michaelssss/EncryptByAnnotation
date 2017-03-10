package com.michaelssss;

public class TestECB
{
    public static void main(String[] s)
    {
        TestClass testClass = new TestClass("aaa", "bbb");
        AnnotationHandler handler = new AnnotationHandlerImpl();
        try
        {
            System.out.println("pre " + testClass);
            handler.handleECB(testClass);
            System.out.println("after " + testClass);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private static class TestClass
    {
        @ECB(key = "heiheiheiheiheiheiheiheihei")
        String abc;

        String ccc;

        public TestClass(String abc, String ccc)
        {
            this.abc = abc;
            this.ccc = ccc;
        }

        @Override
        public String toString()
        {
            return "TestClass{" +
                "abc='" + abc + '\'' +
                ", ccc='" + ccc + '\'' +
                '}';
        }
    }
}
