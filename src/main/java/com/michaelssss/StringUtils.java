package com.michaelssss;

/**
 * Created by michaelssss on 2017/3/11.
 */
public class StringUtils {
    public static boolean isNotBlank(String s) {
        return null != s && s.trim().length() != 0;
    }

    public static boolean isBlank(String s) {
        return !isNotBlank(s);
    }
}
