package org.scott.service.impl;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.scott.constant.CommonConstant;
import org.scott.domain.User;
import org.scott.exception.EntityExistException;
import org.scott.repository.UserRepository;
import org.scott.service.UserService;
import org.scott.service.dto.UserDto;
import org.scott.service.dto.UserQueryCriteria;
import org.scott.service.mapstruct.UserMapper;
import org.scott.utils.PageUtils;
import org.scott.utils.QueryHelp;
import org.scott.utils.ValidationUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

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

    @Operation(summary = "按用户名查询用户")
    @Override
    public UserDto findByName(String userName) {
        User user = userRepository.findByUsername(userName);
        return userMapper.toDto(user);
    }

    @Operation(summary = "查询所有用户")
    @Override
    public Object queryAll(UserQueryCriteria userQueryCriteria, Pageable pageable) {
        //这里findall方法的第一个参数是Specification，
        // Specification是一个函数式接口，因为他只有一个抽象方法:toPredicate()，
        // 所以可以用lambda表达式去表示函数式接口
        Page<User> page = userRepository.findAll(
                (root, query, cb) -> QueryHelp.getPredicate(root, userQueryCriteria, cb), pageable);
        //以上代码等价于以下代码：
//        Page<User> page = userRepository.findAll(new Specification<User>() {
//            @Override
//            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
//                return QueryHelp.getPredicate(root, query, criteriaBuilder);
//            }
//        }, pageable);
        return PageUtils.toPage(page.map(userMapper::toDto));
    }

    @Operation(summary = "新增用户")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User resources) {
        if (userRepository.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        if (userRepository.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        if (userRepository.findByPhone(resources.getPhone()) != null) {
            throw new EntityExistException(User.class, "phone", resources.getPhone());
        }
        userRepository.save(resources);
    }

    @Operation(summary = "修改用户")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) throws Exception {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        // 校验
        ValidationUtils.isNull(user.getId(), "User", "id", resources.getId());
        // 若 username, email, phone 与数据库中有重复的，抛出异常
        User byUsername = userRepository.findByUsername(resources.getUsername());
        User byEmail = userRepository.findByEmail(resources.getEmail());
        User byPhone = userRepository.findByPhone(resources.getPhone());
        if (byUsername != null && !user.getId().equals(byUsername.getId())) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        if (byEmail != null && !user.getId().equals(byEmail.getId())) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        if (byPhone != null && !user.getId().equals(byPhone.getId())) {
            throw new EntityExistException(User.class, "phone", resources.getPhone());
        }
        // 否则，update user
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());
        user.setDept(resources.getDept());
        user.setJobs(resources.getJobs());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setGender(resources.getGender());
        String password = resources.getPassword() == null ?
                CommonConstant.DEFAULT_PASSWORD : resources.getPassword();
        user.setPassword(password);
        userRepository.save(user);
    }

    @Operation(summary = "根据用户ID查询用户")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserDto findById(Long id) {
        User user = userRepository.findById(id).orElseGet(User::new);
        // 校验非空
        ValidationUtils.isNull(user.getId(), "User", "id", id);
        // user -> userDto
        return userMapper.toDto(user);
    }

    @Operation(summary = "删除用户")
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        userRepository.deleteAllById(ids);
    }

}
