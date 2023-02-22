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
 *  * //继承JpaRepository之后，会默认使用SimpleJpaRepository这个实现类作为UserRepository接口的实体bean
 *  * //继承JpaSpecificationExecutor是为了实现动态查询
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    /**
     * 根据用户名查询User
     * @param username 用户名
     * @return User
     */
    User findByUsername(String username);
    /**
     * 根据邮件查询 User
     * @param email 邮件
     * @return User
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询 User
     * @param phone 手机号
     * @return User
     */
    User findByPhone(String phone);
}
