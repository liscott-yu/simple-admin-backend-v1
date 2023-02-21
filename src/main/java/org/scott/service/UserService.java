package org.scott.service;

import org.scott.service.dto.UserDto;
import org.springframework.stereotype.Service;

/**
 * project name  simple-admin-backedv1
 * filename  UserService
 * @author liscott
 * @date 2023/2/21 16:10
 * description  TODO
 */
@Service
public interface UserService {
    /**
     * 根据用户名查询UserDto
     * @param userName 用户名
     * @return UserDto
     */
    UserDto findByName(String userName);
}
