package com.michaelssss;

import java.lang.annotation.*;


@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface Encryption {
    String encryptor();

    String key() default "";
}
