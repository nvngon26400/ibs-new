package com.sbisec.helios.ap.common.config;

import java.time.Duration;

import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

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
}
