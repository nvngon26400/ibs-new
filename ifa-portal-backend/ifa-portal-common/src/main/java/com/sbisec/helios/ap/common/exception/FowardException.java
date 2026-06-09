package com.sbisec.helios.ap.common.exception;

public class FowardException extends RuntimeException {

    private static final long serialVersionUID = 3516368864202322891L;

    public FowardException(Throwable cause) {
        super("FowardException occured.", cause);
    }

    public FowardException(String message) {
        super(message);
    }

    public FowardException(String message, Throwable cause) {
        super(message, cause);
    }
}
