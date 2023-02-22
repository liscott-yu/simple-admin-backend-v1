package org.scott.service.Impl;

import lombok.RequiredArgsConstructor;
import org.scott.domain.Role;
import org.scott.repository.RoleRepository;
import org.scott.service.RoleService;
import org.scott.service.dto.small.RoleSmallDto;

import org.scott.service.mapstruct.RoleSmallMapper;

import org.springframework.stereotype.Service;

import java.util.*;

/**
 * project name  my-eladmin-backend-v2
 * filename  RoleServiceImpl
 * @author liscott
 * @date 2023/1/5 14:26
 * description  TODO
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleSmallMapper roleSmallMapper;


    @Override
    public List<RoleSmallDto> findByUsersId(Long id) {
        Set<Role> roleSet = roleRepository.findByUserId(id);
        List<Role> roleList = new ArrayList<>(roleSet);
        return roleSmallMapper.toDto(roleList);
    }


}