package org.scott.service;

import org.scott.service.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * project name  simple-admin-backedv1
 * filename  DataService
 * @author liscott
 * @date 2023/2/22 14:02
 * description  数据权限 服务类
 */
@Service
public interface DataService {
    /**
     * 根据UserDto获取 用户数据权限
     * @param userDto 用户
     * @return 数据权限
     */
    List<Long> getDeptIds(UserDto userDto);
}
