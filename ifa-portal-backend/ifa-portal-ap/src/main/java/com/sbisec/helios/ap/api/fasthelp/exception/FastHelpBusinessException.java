package com.sbisec.helios.ap.api.fasthelp.exception;

/**
 * Business exception handling class of Fasthelp
 * 
 * @author dalian
 * @date 04/29/2025
 */
public class FastHelpBusinessException extends Exception {

    private static final long serialVersionUID = 1L;

    public FastHelpBusinessException() {
        super();
    }

    public FastHelpBusinessException(String msg) {
        super(msg);
    }

    public FastHelpBusinessException(String msg, Throwable thro) {
        super(msg, thro);
    }

    public FastHelpBusinessException(Throwable thro) {
        super(thro);
    }

    private String message;
    private String errorCode;
    private Integer statusCode;

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the errorCode
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @param errorCode the errorCode to set
     */
    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * @return the statusCode
     */
    public Integer getStatusCode() {
        return statusCode;
    }

    /**
     * @param statusCode the statusCode to set
     */
    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

}
