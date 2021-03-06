package org.social.it.exception;

public class DataExtractionException extends RuntimeException{

    public DataExtractionException(String s) {
        super(s);
    }

    public DataExtractionException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public static void throwIf(boolean condition, String message){
        if(condition) throw new DataExtractionException(message);
    }
}
