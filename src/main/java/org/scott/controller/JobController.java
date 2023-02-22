package org.scott.controller;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.scott.service.JobService;
import org.scott.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * project name  simple-admin-backedv1
 * filename  JobService
 * @author liscott
 * @date 2023/2/22 16:29
 * description  TODO
 */
@RestController
@RequiredArgsConstructor
@Api(tags = "系统：岗位管理")
@RequestMapping(value = "/api/job")
public class JobController {
    private final JobService jobService;

    @GetMapping
    @Operation(summary = "查询岗位")
    @PreAuthorize("hasAnyAuthority('job:list', 'user:list', 'admin')")
    public ResponseEntity<Object> queryJob(JobQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(jobService.queryAll(criteria, pageable), HttpStatus.OK);
    }
}
