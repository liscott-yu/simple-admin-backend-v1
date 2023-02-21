package org.scott.service.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * project name  simple-admin-backedv1
 * filename  AuthUserDto
 *
 * @author liscott
 * @date 2023/2/21 14:41
 * description  粗粒度的用户
 */
@Setter
@Getter
public class AuthUserDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;

    private String code;
    private String uuid = "";
}
