package com.michaelssss;

import java.lang.reflect.Field;

class FieldUtils {
    static boolean isStringType(Field field) {
        return field.getType().equals(String.class);
    }

    static boolean isBytesBuff(Field field) {
        return field.getType().equals(byte[].class);
    }
}
