package com.social.it.exception;

public class DataExtractionException extends RuntimeException{

    public DataExtractionException(String s) {
        super(s);
    }

    public DataExtractionException(String s, Throwable throwable) {
        super(s, throwable);
    }
}
