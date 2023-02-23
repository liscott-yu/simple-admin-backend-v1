package org.scott.config.jwt;

import io.jsonwebtoken.ExpiredJwtException;
import org.apache.commons.lang3.StringUtils;
import org.scott.config.SecurityProperties;
import org.scott.service.OnlineUserService;
import org.scott.service.dto.JwtUserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * project name  simple-admin-backedv1
 * filename  TokenFilter
 * @author liscott
 * @date 2023/2/23 10:47
 * description  TODO
 */
public class TokenFilter extends GenericFilterBean {
    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    private final TokenProvider tokenProvider;
    private final SecurityProperties properties;
    private final OnlineUserService onlineUserService;

    /**
     * @param tokenProvider Token
     * @param properties   JWT
     * @param onlineUserService 在线用户
     */
    public TokenFilter(TokenProvider tokenProvider, SecurityProperties properties, OnlineUserService onlineUserService) {
        this.tokenProvider = tokenProvider;
        this.properties = properties;
        this.onlineUserService = onlineUserService;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        // 从 request 中解析出 token
        String token = resolveToken(httpServletRequest);
        // token为空，不查 redis
        if (StringUtils.isNotBlank(token)) {
            JwtUserDto onlineUser = null;
            try {
                onlineUser = onlineUserService.getOne(properties.getOnlineKey() + token);
            } catch (ExpiredJwtException e) {
                log.error(e.getMessage());
            }
            if (onlineUser != null && StringUtils.isNotBlank(token)) {
                //获得 认证信息并在上下文中 设置认证信息（同 login)
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // token 续期
                tokenProvider.checkRenewal(token);
            }
        }
        // 过滤
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * 从 request 中解析出 token，并去掉Token
     * @param request 网络请求
     * @return token
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉 token prefix
            return bearerToken.replace(properties.getTokenStartWith(), "");
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }
}
