package org.scott.service.dto;

import lombok.Data;
import org.scott.annotattion.DataPermission;
import org.scott.annotattion.Query;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * project name  simple-admin-backedv1
 * filename  UserQueryCriteria
 * @author liscott
 * @date 2023/2/22 13:46
 * description 查询条件类，这个类的参数的具体值由前端传过来，
 * 如果这个类的属性名跟Pageable重复，则两个字段都会赋予同样的值
 */
@Data
@DataPermission(fieldName = "id", joinName = "dept")
public class UserQueryCriteria implements Serializable {
    @Query(type = Query.Type.GREATER_THAN)
    private Long id;

    @Query(propName = "id", type = Query.Type.IN, joinName = "dept")
    private Set<Long> deptIds = new HashSet<>();

    /**
     * //用作三个字段的模糊查询，假设blurry="163"，则表示要查询email、username、nickName其中一个字段包含163的用户
     * //用来满足只有一个搜索框但是要搜多个字段的需求，这样在搜索框中输入邮箱、用户名、昵称其中一个都可以搜到该用户
     */
    @Query(blurry = "email, username, nickName")
    private String blurry;
}
