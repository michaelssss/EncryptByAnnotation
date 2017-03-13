package com.michaelssss.decryptor;

public interface Decryptor {
    byte[] decrypt(byte[] encryptObject);

    byte[] decrypt(byte[] encryptObject, byte[] key);
}
