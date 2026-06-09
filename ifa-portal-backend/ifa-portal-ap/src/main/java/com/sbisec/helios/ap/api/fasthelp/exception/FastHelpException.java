package com.sbisec.helios.ap.api.fasthelp.exception;

/**
 * Fasthelp用の基底の例外クラス
 */
public class FastHelpException extends Exception {

    /** SerialVersionUID */
    private static final long serialVersionUID = 1L;

    /**
     * コンストラクタ
     */
    public FastHelpException() {
        super();
    }

    /**
     * コンストラクタ
     * @param message 詳細メッセージ
     */
    public FastHelpException(final String message) {
        super(message);
    }

    /**
     * コンストラクタ
     * @param message 詳細メッセージ
     * @param cause 原因
     */
    public FastHelpException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * コンストラクタ
     * @param cause 原因
     */
    public FastHelpException(final Throwable cause) {
        super(cause);
    }
}
