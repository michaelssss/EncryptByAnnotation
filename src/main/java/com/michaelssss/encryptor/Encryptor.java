package com.michaelssss.encryptor;
public interface Encryptor
{
    byte[] encrypt(byte[] plainObject);

    byte[] encrypt(byte[] plainObject, byte[] key);
}
