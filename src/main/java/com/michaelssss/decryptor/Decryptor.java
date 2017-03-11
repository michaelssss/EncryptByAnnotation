package com.michaelssss.decryptor;

/**
 * Created by michaelssss on 2017/3/11.
 */
public interface Decryptor {
    byte[] decrypt(byte[] plainObject);

    byte[] decrypt(byte[] plainObject, byte[] key);
}
