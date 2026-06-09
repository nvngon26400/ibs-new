package com.sbisec.helios.gw.common.config;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import com.sbisec.helios.gw.common.interceptor.HttpPostCheckInterceptor;
import com.sbisec.helios.gw.common.interceptor.HttpSessionCheckInterceptor;
import com.sbisec.helios.gw.common.interceptor.IfaCustomerCommonCheckInterceptor;
import com.sbisec.helios.gw.common.interceptor.IfaLogoutHandler;
import com.sbisec.helios.gw.common.interceptor.IfapExtApiInterceptor;
import com.sbisec.helios.gw.common.interceptor.ParameterLogInterceptor;
import com.sbisec.helios.gw.common.interceptor.PermissionCheckInterceptor;
import com.sbisec.helios.gw.common.interceptor.RequestRestrictionCheckInterceptor;
import com.sbisec.helios.gw.common.interceptor.ServiceStatusCheckInterceptor;
import com.sbisec.helios.gw.common.interceptor.SetRequestTimeInterceptor;
import com.sbisec.helios.gw.common.service.TokenService;
import com.sbisec.helios.gw.common.service.impl.TokenServiceImpl;

@Configuration
public class BeanConfig {
    
    /**
     * ログ出力用のフィルタ
     *
     * @return ログ出力用のフィルタのインスタンス
     */
    @Bean
    CommonsRequestLoggingFilter requestLoggingFilter() {
        
        CommonsRequestLoggingFilter filter = new CommonsRequestLoggingFilter();
        filter.setIncludeClientInfo(true);
        filter.setIncludeQueryString(true);
        filter.setIncludeHeaders(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(1024);
        return filter;
    }
    
    /**
     * トークンチェックサービス
     *
     * @param redisConnectionFactory RedisCacheManagerを生成するためのFactoryオブジェクト
     * @return トークンチェックサービスのインスタンス 
     */
    @Bean
    TokenService tokenService(RedisConnectionFactory redisConnectionFactory) {
        
        return new TokenServiceImpl(cacheManager(redisConnectionFactory));
    }
    
    @Bean
    IfaLogoutHandler ifaLogoutHandler() {
        return new IfaLogoutHandler();
    }

    /**
     * RedisCacheManager
     *
     * @param redisConnectionFactory RedisCacheManagerを生成するためのFactoryオブジェクト
     * @return RedisCacheManagerのインスタンス 
     */
    @Bean
    CacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        
        // RedisCacheConfigurationの定義
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1));
        
        RedisCacheManager.RedisCacheManagerBuilder builder = RedisCacheManager.builder(redisConnectionFactory)
                .cacheDefaults(redisCacheConfiguration);
        
        return builder.build();
    }
    
    // Interceptor
    /**
     * Beanを登録する（ServiceStatusCheckInterceptor）。<br/>
     * ignorePathから始まる場合はチェック対象外とする。
     *
     * @return Beanインスタンス。
     */
    @Bean
    ServiceStatusCheckInterceptor serviceStatusCheckInterceptor() {
        
        List<String> ignorePathList = new ArrayList<String>(
                Arrays.asList("/login", "/common/logout", "/common/commonCodeCodeList",
                        "/common/ifaCacheManageRemoveCustomerInfo", "/common/ifaCheckUtilCheckFullWidthCharacter",
                        "/common/ifaLinkUrlUrlAcquire", "/common/ifaLinkUrlHtmlAcquire", "/error", "/alive", "/ifa-portal-gw/alive"));
        List<String> ignoreScreenIdList = new ArrayList<String>();
        
        return new ServiceStatusCheckInterceptor(ignorePathList, ignoreScreenIdList);
    }

    /**
     * Beanを登録する（SetRequestTimeInterceptor）。
     *
     * @return Beanインスタンス。
     */
    @Bean
    SetRequestTimeInterceptor setRequestTimeInterceptor() {
        
        return new SetRequestTimeInterceptor();
    }

    /**
     * Beanを登録する（HttpSessionCheckInterceptor）。
     *
     * @return Beanインスタンス。
     */
    @Bean
    HttpSessionCheckInterceptor httpSessionCheckInterceptor() throws Exception {
        
        return new HttpSessionCheckInterceptor();
    }

    /**
     * Beanを登録する（PermissionCheckInterceptor）。
     *
     * @return Beanインスタンス。
     */
    @Bean
    PermissionCheckInterceptor permissionCheckInterceptor() throws Exception {
        
        return new PermissionCheckInterceptor();
    }

    /**
     * Beanを登録する（IfaCustomerCommonCheckInterceptor）。
     *
     * @return Beanインスタンス。
     */
    @Bean
    IfaCustomerCommonCheckInterceptor ifaCustomerCommonCheckInterceptor() throws Exception {

        return new IfaCustomerCommonCheckInterceptor();
    }

    /**
     * Beanを登録する（ParameterLogInterceptor）。
     *
     * @param cacheManager キャッシュマネージャー。
     * @param tokenService トークチェックサービス。
     * @return Beanインスタンス。
     */
    @Bean
    ParameterLogInterceptor parameterLogInterceptor(CacheManager cacheManager, TokenService tokenService) {
        
        return new ParameterLogInterceptor(cacheManager, tokenService);
    }

    /**
     * Beanを登録する（HttpPostCheckInterceptor）。
     *
     * @return Beanインスタンス。
     */
    @Bean
    HttpPostCheckInterceptor httpPostCheckInterceptor() {
        
        List<String> ignorePathList = new ArrayList<String>(Arrays.asList("/common/logout", "/error"));
        return new HttpPostCheckInterceptor(ignorePathList);
    }
    
    /**
     * Beanを登録する（IfapExtApiInterceptor）。
     *
     * @param cacheManager キャッシュマネージャー。
     * @return Beanインスタンス。
     */
    @Bean
    IfapExtApiInterceptor ifapExtApiInterceptor(CacheManager cacheManager) throws Exception {
        
        return new IfapExtApiInterceptor(cacheManager);
    }
    
    /**
     * Beanを登録する（RequestRestrictionCheckInterceptor）。
     *
     * @param cacheManager キャッシュマネージャー。
     * @return Beanインスタンス。
     */
    @Bean
    RequestRestrictionCheckInterceptor requestRestrictionCheckInterceptor(CacheManager cacheManager) {
        
        return new RequestRestrictionCheckInterceptor(cacheManager);
    }
}
