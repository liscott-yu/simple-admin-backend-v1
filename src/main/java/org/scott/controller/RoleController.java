package org.scott.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.scott.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * project name  simple-admin-backedv1
 * filename  RoleController
 * @author liscott
 * @date 2023/2/22 16:20
 * description  TODO
 */
@Api(tags = "系统：角色管理")
@RequestMapping(value = "/api/roles")
@RequiredArgsConstructor
@RestController
public class RoleController {
    private final RoleService roleService;

    @Operation(summary = "返回全部的角色")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('roles:list', 'user:add', 'user:edit')")
    public ResponseEntity<Object> queryAllRole() {
        return new ResponseEntity<>(roleService.queryAll(), HttpStatus.OK);
    }
}
