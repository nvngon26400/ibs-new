package com.sbisec.helios.ap.api.fasthelp.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.sbisec.helios.ap.api.fasthelp.exception.FastHelpException;

/**
 * FastHelp API の設定情報を読み込むユーティリティクラス
 */
@Component
public class FastHelpConfig {

    /**
     * コンストラクタ
     * @throws FastHelpException 例外
     */
    public FastHelpConfig() throws FastHelpException {
    }

    /** プロトコル */
    @Value("${fasthelpApi.host.protocol:}")
    private String hostProtocol;

    /** CCS-IPアドレス */
    @Value("${ccs.host.ip:}")
    private String hostIpCcs;

    /** FastHelp-IPアドレス */
    @Value("${fasthelpApi.host.ip:}")
    private String hostIpFasthelp;

    
    /** ポート */
    @Value("${fasthelpApi.host.port:}")
    private String hostPort;

    /** クライアントの接続タイムアウト */
    @Value("${fasthelpApi.http.client.connectTimeout:3}")
    private Long httpClientConnectTimeout;

    /** クライアントの読み取りタイムアウト */
    @Value("${fasthelpApi.http.client.readTimeout:15}")
    private Long httpClientReadTimeout;

    /** クライアントの書き込みタイムアウト */
    @Value("${fasthelpApi.http.client.writeTimeout:15}")
    private Long httpClientWriteTimeout;

    /** クライアントのコネクションプールタイムアウト */
    @Value("${fasthelpApi.http.client.connPoolTimeout:10}")
    private Integer httpClientConnPoolTimeout;

    /** クライアントの最大PoolCache数 */
    @Value("${fasthelpApi.http.client.connPoolCachedMax:5}")
    private Integer httpClientConnPoolCachedMax;

    /** クライアントのリトライ回数 */
    @Value("${fasthelpApi.http.client.retryCount:0}")
    private Integer httpClientRetryCount;

    /**
     * プロトコルを取得する。
     * @return プロトコル
     */
    public String getHostProtocol() {
        return hostProtocol;
    }

    /**
     * CCS-IPアドレスを取得する。
     * @return IPアドレス
     */
    public String getHostIpCcs() {
        return hostIpCcs;
    }

    /**
     * FastHelp-IPアドレスを取得する。
     * @return IPアドレス
     */
    public String getHostIpFasthelp() {
        return hostIpFasthelp;
    }

    /**
     * ポートを取得する。
     * @return ポート
     */
    public String getHostPort() {
        return hostPort;
    }

    /**
     * クライアントの接続タイムアウトを取得します。
     * @return 接続タイムアウト
     */
    public Long getHttpClientConnectTimeout() {
        return httpClientConnectTimeout;
    }

    /**
     * クライアントの読み取りタイムアウトを取得します。
     * @return 読み取りタイムアウト
     */
    public Long getHttpClientReadTimeout() {
        return httpClientReadTimeout;
    }

    /**
     * クライアントの書き込みタイムアウトを取得します。
     * @return 書き込みタイムアウト
     */
    public Long getHttpClientWriteTimeout() {
        return httpClientWriteTimeout;
    }

    /**
     * クライアントのコネクションプールタイムアウトを取得します。
     * @return コネクションプールタイムアウト
     */
    public Integer getHttpClientConnPoolTimeout() {
        return httpClientConnPoolTimeout;
    }

    /**
     * クライアントの最大PoolCache数を取得する。
     * @return 最大PoolCache数
     */
    public Integer getHttpClientConnPoolCachedMax() {
        return httpClientConnPoolCachedMax;
    }

    /**
     * クライアントのリトライ回数を取得する。
     * @return リトライ回数
     */
    public Integer getHttpClientRetryCount() {
        return httpClientRetryCount;
    }
}