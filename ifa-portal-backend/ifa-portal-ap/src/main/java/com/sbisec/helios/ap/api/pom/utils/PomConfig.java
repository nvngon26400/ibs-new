package com.sbisec.helios.ap.api.pom.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: Pom api properties
 * @author shuchen.xin
 * @date: 06/02/2021
 */
@Component
public class PomConfig {

    /**
     * コンストラクタ（例外をスロー）
     * @throws PomException 例外
     */
    public PomConfig() throws PomException {
    }

    /** プロトコル */
    @Value("${pomApi.host.protocol:}")
    private String hostProtocol;

    /** IPアドレス */
    @Value("${pomApi.host.ip:}")
    private String hostIp;

    /** ポート */
    @Value("${pomApi.host.port:}")
    private String hostPort;

    /** クライアントの接続タイムアウト */
    @Value("${pomApi.http.client.connectTimeout:3}")
    private Long httpClientConnectTimeout;

    /** クライアントの読み取りタイムアウト */
    @Value("${pomApi.http.client.readTimeout:15}")
    private Long httpClientReadTimeout;

    /** クライアントの書き込みタイムアウト */
    @Value("${pomApi.http.client.writeTimeout:15}")
    private Long httpClientWriteTimeout;

    /** クライアントのコネクションプールタイムアウト */
    @Value("${pomApi.http.client.connPoolTimeout:10}")
    private Integer httpClientConnPoolTimeout;

    /** クライアントの最大PoolCache数 */
    @Value("${pomApi.http.client.connPoolCachedMax:5}")
    private Integer httpClientConnPoolCachedMax;

    /** クライアントのリトライ回数 */
    @Value("${pomApi.http.client.retryCount:2}")
    private Integer httpClientRetryCount;

    /**
     * ホストプロトコルを取得
     * @return ホストプロトコル
     */
    public String getHostProtocol() {
        return hostProtocol;
    }

    /**
     * ホストIPを取得
     * @return ホストIP
     */
    public String getHostIp() {
        return hostIp;
    }

    /**
     * ホストポートを取得
     * @return ホストポート
     */
    public String getHostPort() {
        return hostPort;
    }

    /**
     * 接続タイムアウトを取得
     * @return 接続タイムアウト
     */
    public Long getHttpClientConnectTimeout() {
        return httpClientConnectTimeout;
    }

    /**
     * 読み込みタイムアウトを取得
     * @return 読み込みタイムアウト
     */
    public Long getHttpClientReadTimeout() {
        return httpClientReadTimeout;
    }

    /**
     * 書き出しタイムアウトを取得
     * @return 書き出しタイムアウト
     */
    public Long getHttpClientWriteTimeout() {
        return httpClientWriteTimeout;
    }

    /**
     * 接続プールタイムアウトを取得
     * @return 接続プールタイムアウト
     */
    public Integer getHttpClientConnPoolTimeout() {
        return httpClientConnPoolTimeout;
    }

    /**
     * 最大接続キャッシュ数を取得
     * @return 最大接続キャッシュ数
     */
    public Integer getHttpClientConnPoolCachedMax() {
        return httpClientConnPoolCachedMax;
    }

    /**
     * リトライ数を取得
     * @return リトライ数
     */
    public Integer getHttpClientRetryCount() {
        return httpClientRetryCount;
    }
}
