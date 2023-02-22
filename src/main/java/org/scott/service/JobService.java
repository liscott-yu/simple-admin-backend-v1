package org.scott.service;

import org.scott.service.dto.JobQueryCriteria;
import org.springframework.data.domain.Pageable;

import java.util.Map;

/**
 * project name  simple-admin-backedv1
 * filename  JobService
 * @author liscott
 * @date 2023/2/22 14:54
 * description  TODO
 */
public interface JobService {
    /**
     * 分页查询
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(JobQueryCriteria criteria, Pageable pageable);
}
