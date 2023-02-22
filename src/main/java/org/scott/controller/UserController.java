package org.scott.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.scott.service.UserService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @Operation(summary = "查询用户")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('user:list', 'admin')")
    public ResponseEntity<Object> queryUser(Pageable pageable) {
        return new ResponseEntity<>(userService.queryAll(pageable), HttpStatus.OK);
    }
}