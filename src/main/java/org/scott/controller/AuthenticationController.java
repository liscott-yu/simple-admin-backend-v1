package org.scott.controller;

import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.scott.constant.CaptchaConstant;
import org.scott.constant.CommonConstant;
import org.scott.service.dto.AuthUserDto;
import org.scott.utils.RedisUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "用户登录")
    @GetMapping(value = "/login")
    public ResponseEntity<Object> login(@RequestBody @Validated AuthUserDto authUserDto, HttpServletRequest request) throws Exception {
        // 从redis获取验证码后,删除之
        String captchaValue = (String) redisUtils.get(authUserDto.getUuid());
        redisUtils.del(authUserDto.getUuid());

        if (StringUtils.isBlank(captchaValue)) {
            throw new Exception("验证码过期或不存在");
        }
        if (StringUtils.isBlank(authUserDto.getCode()) || !StringUtils.equalsIgnoreCase(captchaValue, authUserDto.getCode())) {
            throw new Exception("验证码错误");
        }

        // 已通过验证码验证
        return ResponseEntity.ok("验证码通过,但未进行身份认证");
    }

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

    private String getUuidWithPrefix() {
        return CommonConstant.UUID_PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
    }
}
