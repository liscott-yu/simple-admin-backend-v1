package org.scott.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.scott.service.DeptService;
import org.scott.service.dto.DeptDto;
import org.scott.service.dto.DeptQueryCriteria;
import org.scott.utils.PageUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * project name  simple-admin-backedv1
 * filename  DeptController
 * @author liscott
 * @date 2023/2/22 16:07
 * description  TODO
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：部门管理")
@RequestMapping(value = "/api/dept")
public class DeptController {
    private final DeptService deptService;

    @Operation(summary = "查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list', 'dept:list')")
    public ResponseEntity<Object> queryDept(DeptQueryCriteria criteria) throws Exception {
        List<DeptDto> deptDtos = deptService.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtils.toPage(deptDtos, deptDtos.size()), HttpStatus.OK);
    }
}
