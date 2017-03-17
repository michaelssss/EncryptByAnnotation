package com.michaelssss;


public interface AnnotationHandler {
    /**
     * handle every Annotation
     * the implement should declear what dose it handle
     * such as
     *
     * @param e
     * @throws NoSupportEncryptTypeException
     * @see EncryptionAnnotationHandlerImpl
     * @see DecryptionAnnotationHandlerImpl
     */
    void handle(Object e)
            throws NoSupportEncryptTypeException;
}
