package org.scott.config;

import lombok.Getter;
import lombok.Setter;

/**
 * project name  simple-admin-backedv1
 * filename  SecurityProperties
 * @author liscott
 * @date 2023/2/23 9:56
 * description  TODO
 */
@Setter
@Getter
public class SecurityProperties {
    /** Request Header: Authorization*/
    private String header;
    /** token前缀，样如：前缀 Bearer */
    private String tokenStartWith;
    /** 必须使用最少88位的Base64对该令牌进行编码 */
    private String base64Secret;
    /**TOKEN 过期时间，单位：秒*/
    private Long tokenValidityInSeconds;
    /**在线用户 key，根据 key 查询 redis 中在线用户的数据*/
    private String onlineKey;
    /** 验证码 key */
    private String codeKey;
    /** token 续期 */
    private Long detect;
    /** token 续期时间 */
    private Long renew;

    public String getTokenStartWith() {
        return tokenStartWith + " ";
    }
}
