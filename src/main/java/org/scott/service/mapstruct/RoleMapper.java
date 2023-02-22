package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.Role;
import org.scott.service.dto.RoleDto;

/**
 * project name  simple-admin-backedv1
 * filename  RoleMapper
 * @author liscott
 * @date 2023/2/22 15:06
 * description  TODO
 */
@Mapper(componentModel = "spring", uses = {MenuMapper.class, DeptMapper.class},
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoleMapper extends BaseMapper<RoleDto, Role> {
}
