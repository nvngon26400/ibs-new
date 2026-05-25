package com.sbisec.helios.ap.api.ccsApi.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * CCS API の設定情報を読み込むユーティリティクラス
 */
@Component
public class CcsApiConfig {

    /**
     * コンストラクタ
     */
    public CcsApiConfig() {
    }

    /** プロトコル */
    @Value("${ccsApi.host.protocol:}")
    private String hostProtocol;

    /** IPアドレス */
    @Value("${ccsApi.host.ip:}")
    private String hostIp;
    
    /** ポート */
    @Value("${ccsApi.host.port:}")
    private String hostPort;

    /** クライアントの接続タイムアウト */
    @Value("${ccsApi.http.client.connectTimeout:3}")
    private Long httpClientConnectTimeout;

    /** クライアントの読み取りタイムアウト */
    @Value("${ccsApi.http.client.readTimeout:15}")
    private Long httpClientReadTimeout;

    /** クライアントの書き込みタイムアウト */
    @Value("${ccsApi.http.client.writeTimeout:15}")
    private Long httpClientWriteTimeout;

    /** クライアントのコネクションプールタイムアウト */
    @Value("${ccsApi.http.client.connPoolTimeout:10}")
    private Integer httpClientConnPoolTimeout;

    /** クライアントの最大PoolCache数 */
    @Value("${ccsApi.http.client.connPoolCachedMax:5}")
    private Integer httpClientConnPoolCachedMax;

    /** クライアントのリトライ回数 */
    @Value("${ccsApi.http.client.retryCount:0}")
    private Integer httpClientRetryCount;

    /**
     * プロトコルを取得する。
     * @return プロトコル
     */
    public String getHostProtocol() {
        return hostProtocol;
    }

    /**
     * IPアドレスを取得する。
     * @return IPアドレス
     */
    public String getHostIp() {
        return hostIp;
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