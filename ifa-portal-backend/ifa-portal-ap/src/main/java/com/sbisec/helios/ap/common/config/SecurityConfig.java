package com.sbisec.helios.ap.common.config;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

/**
 * Service security authentication configuration.
 * 
 * @Organization SBIBITS DaLian CB Group
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private PropertyHolder prop;
    @Autowired
    protected MessageSourceAccessor message;

    @Bean
    WebSecurityCustomizer webSecurityCustomizer() throws Exception {
        return (web) -> {
            web.ignoring().requestMatchers(prop.getSecurityIgnore());
        };
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.cors()
            .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .anonymous()
            .and();
        return http.build();
    }

    @Bean
    SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    /**
     * Csrf token.
     * 
     * @return
     */
    @Bean
    CsrfTokenRepository getCsrfTokenRepository() {
        CookieCsrfTokenRepository cookie = CookieCsrfTokenRepository.withHttpOnlyFalse();
        cookie.setCookiePath("/");
        return cookie;
    }


    List<AuthenticationProvider> buildProviders() {
        return prop.getEnvLdaps().stream().map(ldap -> {
            ActiveDirectoryLdapAuthenticationProvider provider = new ActiveDirectoryLdapAuthenticationProvider(
                    ldap.getDomain(), ldap.getUrl());
            provider.setConvertSubErrorCodesToExceptions(true);
            provider.setUseAuthenticationRequestCredentials(true);
            return provider;
        }).collect(Collectors.toList());
    }
}
