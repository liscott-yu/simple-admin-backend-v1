package org.scott.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.scott.constant.CommonConstant;
import org.scott.domain.User;
import org.scott.exception.BadRequestException;
import org.scott.service.RoleService;
import org.scott.service.UserService;
import org.scott.service.dto.UserQueryCriteria;
import org.scott.service.dto.small.RoleSmallDto;
import org.scott.utils.SecurityUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * project name  simple-admin-backedv1
 * filename  UserController
 * @author liscott
 * @date 2023/2/22 10:48
 * description  查询用户
 */
@RequestMapping(value = "/api/users")
@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Operation(summary = "查询用户")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:list', 'admin')")
    public ResponseEntity<Object> queryUser(UserQueryCriteria userQueryCriteria, Pageable pageable) {
        return new ResponseEntity<>(userService.queryAll(userQueryCriteria, pageable), HttpStatus.OK);
    }

    @Operation(summary = "新增用户")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('user:add', 'admin')")
    public ResponseEntity<Object> createUser(@Validated @RequestBody User resources) {
        checkLevel(resources);
        String password = resources.getPassword() == null ? CommonConstant.DEFAULT_PASSWORD : resources.getPassword();
        resources.setPassword(passwordEncoder.encode(password));
        userService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * 如果当前用户的角色级别低于创建用户的角色级别，则抛出权限不足的错误
     * @param resources User
     */
    private void checkLevel(User resources) {
        // 当前登录用户的角色级别
        Integer currentLevel = Collections.min(roleService.findByUsersId(SecurityUtils.getCurrentUserId())
                .stream().map(RoleSmallDto::getLevel).collect(Collectors.toList()));
        // 新用户的角色级别
        Integer optLevel = roleService.findByRoles(resources.getRoles());
        // level 1 是最高级别，数值越大级别越低
        if (currentLevel > optLevel) {
            throw new BadRequestException(CommonConstant.MSG_INSUFFICIENT_AUTHORITY);
        }
    }

}
