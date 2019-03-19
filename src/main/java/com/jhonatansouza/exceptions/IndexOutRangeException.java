package com.jhonatansouza.exceptions;

public class IndexOutRangeException extends Exception {

    public IndexOutRangeException() {
    }

    public IndexOutRangeException(String message) {
        super(message);
    }

    public IndexOutRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public IndexOutRangeException(Throwable cause) {
        super(cause);
    }

    public IndexOutRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
