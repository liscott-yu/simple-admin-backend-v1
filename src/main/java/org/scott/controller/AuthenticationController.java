package org.scott.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.scott.config.RsaProperties;
import org.scott.config.SecurityProperties;
import org.scott.config.jwt.TokenProvider;
import org.scott.constant.CaptchaConstant;
import org.scott.constant.CommonConstant;
import org.scott.service.OnlineUserService;
import org.scott.service.dto.AuthUserDto;
import org.scott.service.dto.JwtUserDto;
import org.scott.utils.RedisUtils;
import org.scott.utils.RsaUtils;
import org.scott.utils.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * project name  simple-admin-backedv1
 * filename  AuthenticationController
 * @author liscott
 * @date 2023/2/21 13:57
 * description  身份认证
 */
@RestController
@RequestMapping(value = "/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final RedisUtils redisUtils;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private final SecurityProperties properties;
    private final TokenProvider tokenProvider;
    private final OnlineUserService onlineUserService;

    @Operation(summary = "获取当前登录用户信息")
    @GetMapping(value = "/info")
    public ResponseEntity<Object> getUserInfo() {
        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
    }

    /**
     * 用户登录
     * @param authUserDto 粗粒度用户
     * @param request 请求
     * @return /
     * @throws Exception /
     */
    @Operation(summary = "用户登录")
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody @Validated AuthUserDto authUserDto, HttpServletRequest request) throws Exception {
        // 解密密码
        String password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUserDto.getPassword());
        // 从redis获取验证码后,删除之
        String captchaValue = (String) redisUtils.get(authUserDto.getUuid());
        redisUtils.del(authUserDto.getUuid());

        if (StringUtils.isBlank(captchaValue)) {
            throw new Exception("验证码过期或不存在");
        }
        if (StringUtils.isBlank(authUserDto.getCode()) || !StringUtils.equalsIgnoreCase(captchaValue, authUserDto.getCode())) {
            throw new Exception("验证码错误");
        }

        // 认证
        // 1.根据用户名和解密密码创造一个 UsernamePasswordAuthenticationToken 实例
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authUserDto.getUsername(), password);
        // 2.认证UsernamePasswordAuthenticationToken实例，认证成功则返回包含用户信息的Authentication实例
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(token);
        // 3.设置当前登录用户，这一步是为了可以让其他类或方法通过SecurityContextHolder.getContext().getAuthentication()拿到当前登录的用户
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 4.通过已经认证的Authentication返回UserDetails
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();

        // 生成token, 保存在线信息
        String jwt = tokenProvider.createToken(authentication);
        onlineUserService.save(jwtUserDto, jwt, request);

        // 返回JwtUserDto和token给前端
        Map<String, Object> authInfo = new HashMap<String, Object>(CommonConstant.MAP_INIT_CAPACITY) {{
            put("user", jwtUserDto);
            put("token", properties.getTokenStartWith() + jwt);
        }};

        return ResponseEntity.ok(authInfo);
    }

    /**
     * 生成验证码
     * @return /
     */
    @Operation(summary = "验证码生成")
    @GetMapping(value = "/code")
    public ResponseEntity<Object> getCode() {
        // 获取验证码信息
        Captcha captcha = new ArithmeticCaptcha(CaptchaConstant.CAPTCHA_WIDTH, CaptchaConstant.CAPTCHA_HEIGHT);
        captcha.setLen(CaptchaConstant.CAPTCHA_LENGTH);
        String captchaValue = captcha.text();
        String uuid = getUuidWithPrefix();
        // 把UUID和验证码结果放入redis
        redisUtils.set(uuid, captchaValue, CaptchaConstant.CAPTCHA_EXPIRE_TIME, TimeUnit.MINUTES);
        // 把UUID和验证码信息放入HashMap，返回前端
        Map<String, Object> resultImage = new HashMap<String, Object>(CommonConstant.MAP_INIT_CAPACITY) {{
            // 图片转换成Base64格式
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return ResponseEntity.ok(resultImage);
    }

    /**
     * 带前缀的去掉"-"的uuid
     * @return 带前缀的去掉"-"的uuid
     */
    private String getUuidWithPrefix() {
        return CommonConstant.UUID_PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
    }
}
