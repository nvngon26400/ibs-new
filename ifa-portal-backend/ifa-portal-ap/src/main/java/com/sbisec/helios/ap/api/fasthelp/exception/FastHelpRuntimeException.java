package com.sbisec.helios.ap.api.fasthelp.exception;

/**
 * Fasthelpの非検査例外
 */
public class FastHelpRuntimeException extends RuntimeException {

    /** SerialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     */
    public FastHelpRuntimeException() {
        super();
    }

    /**
     * コンストラクタ
     * @param message 詳細メッセージ
     */
    public FastHelpRuntimeException(final String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * @param message 詳細メッセージ
     * @param cause 原因
     */
    public FastHelpRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     * @param cause 原因
     */
    public FastHelpRuntimeException(final Throwable cause) {
        super(cause);
    }
}
