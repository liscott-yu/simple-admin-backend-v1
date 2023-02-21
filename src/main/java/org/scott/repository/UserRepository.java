package org.scott.repository;

import org.scott.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * project name  simple-admin-backedv1
 * filename  UserRepository
 * @author liscott
 * @date 2023/2/21 16:08
 * description //<User, Long>前者代表要查哪个表，后者代表id的类型
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户名查询User
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);
}
