package org.scott.service.impl;

import lombok.RequiredArgsConstructor;
import org.scott.domain.Job;
import org.scott.repository.JobRepository;
import org.scott.service.JobService;
import org.scott.service.dto.JobQueryCriteria;
import org.scott.service.mapstruct.JobMapper;
import org.scott.utils.PageUtils;
import org.scott.utils.QueryHelp;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * project name  simple-admin-backedv1
 * filename  JobServiceImpl
 * @author liscott
 * @date 2023/2/22 15:29
 * description  TODO
 */
@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;

    @Override
    public Map<String, Object> queryAll(JobQueryCriteria criteria, Pageable pageable) {
        Page<Job> page = jobRepository.findAll(((root, query, criteriaBuilder) ->
                QueryHelp.getPredicate(root, criteria, criteriaBuilder)), pageable);
        return PageUtils.toPage(page.map(jobMapper::toDto).getContent(), page.getTotalElements());
    }
}
