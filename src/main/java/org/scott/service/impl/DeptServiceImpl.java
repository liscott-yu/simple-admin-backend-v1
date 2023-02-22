package org.scott.service.impl;

import lombok.RequiredArgsConstructor;
import org.scott.domain.Dept;
import org.scott.repository.DeptRepository;
import org.scott.service.DeptService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * project name  simple-admin-backedv1
 * filename  DeptServiceImpl
 *
 * @author liscott
 * @date 2023/2/22 14:08
 * description  TODO
 */
@Service
@RequiredArgsConstructor
public class DeptServiceImpl implements DeptService {
    private final DeptRepository deptRepository;

    @Override
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public Set<Dept> findByRoleId(Long id) {
        return deptRepository.findByRoleId(id);
    }

    @Override
    public List<Long> getDeptChildren(List<Dept> deptList) {
        ArrayList<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
            if (dept != null && dept.getEnabled()) {
                List<Dept> depts = deptRepository.findByPid(dept.getId());
                if (depts.size() != 0) {
                    list.addAll(getDeptChildren(depts));
                }
                list.add(dept.getId());
            }
        });
        return list;
    }
}
