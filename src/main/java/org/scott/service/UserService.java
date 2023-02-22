package org.scott.service;

import org.scott.service.dto.UserDto;
import org.springframework.data.domain.Pageable;
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

    /**
     * 根据分页信息查询所有用户
     * @param pageable 分页信息
     * @return Object
     */
    Object queryAll(Pageable pageable);
}
