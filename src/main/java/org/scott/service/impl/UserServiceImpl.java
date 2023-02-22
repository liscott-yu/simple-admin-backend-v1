package org.scott.service.impl;

import lombok.RequiredArgsConstructor;
import org.scott.domain.User;
import org.scott.repository.UserRepository;
import org.scott.service.UserService;
import org.scott.service.dto.UserDto;
import org.scott.service.mapstruct.UserMapper;
import org.scott.utils.PageUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * project name  simple-admin-backedv1
 * filename  UserServiceImpl
 * @author liscott
 * @date 2023/2/21 16:11
 * description  TODO
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDto findByName(String userName) {
        User user = userRepository.findByUsername(userName);
        return userMapper.toDto(user);
    }

    @Override
    public Object queryAll(Pageable pageable) {
        Page<User> pages = userRepository.findAll(pageable);
        return PageUtils.toPage(pages.map(userMapper::toDto));
    }
}
