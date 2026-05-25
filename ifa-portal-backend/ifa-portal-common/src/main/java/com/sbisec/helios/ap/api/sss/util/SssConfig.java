package com.sbisec.helios.ap.api.sss.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: Sss api properties
 * @author toma.hayashi
 * @date: 11/06/22023
 */
@Component
public class SssConfig {

	public SssConfig() throws SssConnectionException {
	}

    /** プロトコル */
    @Value("${sssApi.host.protocol:}")
    private String hostProtocol;

    /** IPアドレス */
    @Value("${sssApi.host.ip:}")
    private String hostIp;

    /** ポート */
    @Value("${sssApi.host.port:}")
    private String hostPort;

    /** クライアントの接続タイムアウト */
    @Value("${sssApi.http.client.connectTimeout:3}")
    private Long httpClientConnectTimeout;

    /** クライアントの読み取りタイムアウト */
    @Value("${sssApi.http.client.readTimeout:5}")
    private Long httpClientReadTimeout;

    /** クライアントの書き込みタイムアウト */
    @Value("${sssApi.http.client.writeTimeout:10}")
    private Long httpClientWriteTimeout;

    /** クライアントのコネクションプールタイムアウト */
    @Value("${sssApi.http.client.connPoolTimeout:10}")
    private Integer httpClientConnPoolTimeout;

    /** クライアントの最大PoolCache数 */
    @Value("${sssApi.http.client.connPoolCachedMax:5}")
    private Integer httpClientConnPoolCachedMax;

    /** クライアントのリトライ回数 */
    @Value("${sssApi.http.client.retryCount:1}")
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