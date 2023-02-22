package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.Dept;
import org.scott.service.dto.DeptDto;

/**
 * project name  simple-admin-backedv1
 * filename  DeptMapper
 * @author liscott
 * @date 2023/2/22 15:03
 * description  TODO
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DeptMapper extends BaseMapper<DeptDto, Dept> {
}
