package jp.co.sbisec.pcenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;

import jakarta.annotation.PostConstruct;
import jp.co.sbisec.pcenter.api.HttpClientManager;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "pcenter-api")
public class Config {

    private Crypt crypt;
    private Map<String, Integer> clients;
    private Map<String, AppConfig> applications;

    private final Map<Class<?>, String> apis = new HashMap<Class<?>, String>();
    private static final String PACKAGE_ROOT = "jp.co.sbisec.pcenter.dto";

    private static Config instance = null;

    @Autowired
    private Environment env;

    private static final ObjectMapper CONVERT_MAPPER = JsonMapper.builder()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).build()
            .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);

    @PostConstruct
    public void init() throws Exception {
        if (Objects.isNull(instance)) {
            instance = this;

            Binder binder = Binder.get(env);

            if (this.crypt == null) {
                Map<String, Object> cryptMap = binder
                        .bind("pcenter-api.crypt", Bindable.mapOf(String.class, Object.class)).orElse(null);
                if (cryptMap != null) {
                    this.crypt = CONVERT_MAPPER.convertValue(cryptMap, Crypt.class);
                }
            }

            if (this.applications == null || this.applications.isEmpty()) {
                Map<String, Object> rawApps = binder
                        .bind("pcenter-api.applications", Bindable.mapOf(String.class, Object.class)).orElse(null);
                if (rawApps != null) {
                    cleanRawApps(rawApps);

                    this.applications = CONVERT_MAPPER.convertValue(rawApps, CONVERT_MAPPER.getTypeFactory()
                            .constructMapType(HashMap.class, String.class, AppConfig.class));
                }
            }

            this.registClass();

            HttpClientManager.init(instance.clients.get("initial").intValue(), instance.clients.get("max").intValue(),
                    instance.clients.get("connect-timeout").intValue());

            this.appInit();
            Util.init();
        }
    }

    @SuppressWarnings("unchecked")
    private void cleanRawApps(Map<String, Object> rawApps) {
        rawApps.forEach((appName, appConfigMap) -> {
            if (appConfigMap instanceof Map) {
                Map<String, Object> configMap = (Map<String, Object>) appConfigMap;

                configMap.computeIfPresent("loadbalance",
                        (k, v) -> v instanceof String ? ((String) v).replace("-", "") : v);

                Object servers = configMap.get("servers");
                if (servers instanceof Map) {
                    configMap.put("servers", new ArrayList<>(((Map<?, ?>) servers).values()));
                } else if (servers instanceof String s) {
                    if (s.startsWith("[") && s.endsWith("]")) {
                        s = s.substring(1, s.length() - 1);
                    }
                    List<String> serverList = Arrays.asList(s.split("\\s*,\\s*"));
                    configMap.put("servers", serverList);
                }

                if (configMap.containsKey("hash-key")) {
                    configMap.put("hash_key", configMap.remove("hash-key"));
                }
            }
        });
    }

    public static Config get() {
        return instance;
    }

    Config() {
    }

    private void registClass() throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        CachingMetadataReaderFactory factory = new CachingMetadataReaderFactory(resolver);

        String packagePath = PACKAGE_ROOT.replace('.', '/');
        Resource[] resources = resolver.getResources("classpath*:" + packagePath + "/**/*In.class");

        for (Resource resource : resources) {
            MetadataReader reader = factory.getMetadataReader(resource);
            String className = reader.getClassMetadata().getClassName();
            Class<?> clazz = Class.forName(className);

            String[] parts = className.split("\\.");
            if (parts.length > 5) {
                String appName = parts[parts.length - 2];
                apis.put(clazz, appName);
            }
        }
    }

    private void appInit() {
        for (Map.Entry<String, AppConfig> entry : applications.entrySet()) {

            String name = String.format("%s.%s.Initializer", PACKAGE_ROOT, entry.getKey());
            try {
                Class.forName(name).getMethod("init", AppConfig.class).invoke(null, entry.getValue());
            } catch (Exception e) {
                continue;
            }
        }
    }

    public <T> AppConfig getAppConfig(Class<T> type) {
        String appname = apis.get(type);
        return (appname != null) ? applications.get(appname) : applications.get("heracross");
    }
}
