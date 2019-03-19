package com.jhonatansouza.exceptions;

public class PlanetException extends Exception {
    public PlanetException() {
    }

    public PlanetException(String message) {
        super(message);
    }

    public PlanetException(String message, Throwable cause) {
        super(message, cause);
    }

    public PlanetException(Throwable cause) {
        super(cause);
    }

    public PlanetException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
