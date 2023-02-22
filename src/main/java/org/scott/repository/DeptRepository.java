package org.scott.repository;

import org.scott.domain.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
 * project name  simple-admin-backedv1
 * filename  DeprRepository
 * @author liscott
 * @date 2023/2/22 13:27
 * description  TODO
 */
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept>  {
    /**
     * 根据 PID 查询 部门列表
     * @param id pid
     * @return 部门列表
     */
    List<Dept> findByPid(Long id);

    /**
     * 根据 角色ID 查询 部门集合
     * @param roleId 角色ID
     * @return 部门集合
     * nativeQuery=true   表示使用原生SQL，from后面接数据库表名
     * nativeQuery=false 表示使用HQL，from后面接实体类名
     */
    @Query(value = "SELECT d.* FROM sys_dept d, sys_roles_depts rd " +
            "WHERE d.dept_id = rd.dept_id AND rd.role_id = ?1", nativeQuery = true)
    Set<Dept> findByRoleId(Long roleId);

}
