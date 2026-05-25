package com.sbisec.helios.ap.broker.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description: Papy api properties
 * @author katsuhiko.kagoshima
 * @date: 12/23/2022
 */
@Component
public class PapyConfig {

    /**
     * コンストラクタ
     */
    public PapyConfig(){
    }

    /** プロトコル */
    @Value("${papyApi.host.protocol:}")
    private String hostProtocol;

    /** IPアドレス */
    @Value("${papyApi.host.ip:}")
    private String hostIp;

    /** ポート */
    @Value("${papyApi.host.port:}")
    private String hostPort;

    /** クライアントの接続タイムアウト */
    @Value("${papyApi.http.client.connectTimeout:3}")
    private Long httpClientConnectTimeout;

    /** クライアントの読み取りタイムアウト */
    @Value("${papyApi.http.client.readTimeout:15}")
    private Long httpClientReadTimeout;

    /** クライアントの書き込みタイムアウト */
    @Value("${papyApi.http.client.writeTimeout:15}")
    private Long httpClientWriteTimeout;

    /** クライアントのコネクションプールタイムアウト */
    @Value("${papyApi.http.client.connPoolTimeout:10}")
    private Integer httpClientConnPoolTimeout;

    /** クライアントの最大PoolCache数 */
    @Value("${papyApi.http.client.connPoolCachedMax:5}")
    private Integer httpClientConnPoolCachedMax;

    /** クライアントのリトライ回数 */
    @Value("${papyApi.http.client.retryCount:0}")
    private Integer httpClientRetryCount;

    /** filepath */
    @Value("${papyApi.keystore.filepath:}")
    private String keystoreFilepath;

    /** password */
    @Value("${papyApi.keystore.password:}")
    private String keystorePassword;

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

    /**
     * filepathを取得する。
     * @return filepath
     */
    public String getKeystoreFilepath() {
        return keystoreFilepath;
    }

    /**
     * passwordを取得する。
     * @return password
     */
    public String getKeystorePassword() {
        return keystorePassword;
    }
}
