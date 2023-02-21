package org.scott.service.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.scott.base.BaseMapper;
import org.scott.domain.User;
import org.scott.service.dto.UserDto;

/**
 * project name  simple-admin-backedv1
 * filename  UserMapper
 * @author liscott
 * @date 2023/2/21 16:13
 * description  TODO
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserMapper extends BaseMapper<UserDto, User> {
}
