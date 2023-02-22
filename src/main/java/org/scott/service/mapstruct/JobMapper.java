package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.Job;
import org.scott.service.dto.JobDto;

/**
 * project name  simple-admin-backedv1
 * filename  JobMapper
 * @author liscott
 * @date 2023/2/22 15:04
 * description  TODO
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface JobMapper extends BaseMapper<JobDto, Job> {
}
