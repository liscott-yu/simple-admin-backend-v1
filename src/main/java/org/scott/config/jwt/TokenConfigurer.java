package org.scott.config.jwt;

import lombok.RequiredArgsConstructor;
import org.scott.config.SecurityProperties;
import org.scott.service.OnlineUserService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * project name  simple-admin-backedv1
 * filename  TokenConfigurer
 * @author liscott
 * @date 2023/2/23 10:41
 * description  TODO
 */
@RequiredArgsConstructor
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        TokenFilter customFilter = new TokenFilter(tokenProvider, properties, onlineUserService);
        builder.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
