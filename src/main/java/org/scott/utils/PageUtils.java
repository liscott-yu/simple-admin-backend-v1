package org.scott.utils;

import org.scott.constant.CommonConstant;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * project name  simple-admin-backedv1
 * filename  PageUtils
 * @author liscott
 * @date 2023/2/22 10:17
 * description  分页工具
 */
public class PageUtils extends cn.hutool.core.util.PageUtil {

    /**
     * 分页
     * @param page 页码
     * @param size 每页的数据项数
     * @param list 待分页的数据列表
     * @return
     */
    public static List toPage(int page, int size, List list) {
        // 页首
        int fromIndex = page * size;
        // 页尾
        int toIndex = fromIndex + size;
        // 1. list.size() ,[fromIndex, toIndex]
        if (fromIndex > list.size()) {
            return new ArrayList();
        // 2. [fromIndex,list.size(), toIndex]
        } else if (toIndex >= list.size()) {
            return list.subList(fromIndex, list.size());
        // 3. [fromIndex, toIndex],list.size()
        } else {
            return list.subList(fromIndex, toIndex);
        }
    }

    /**
     * Page 数据处理，预防redis反序列化报错
     * @param page subList
     * @return Map
     */
    public static Map<String, Object> toPage(Page page) {
        Map<String, Object> map = new LinkedHashMap<String, Object>(CommonConstant.MAP_INIT_CAPACITY) {{
            put("content", page.getContent());
            put("totalElements", page.getTotalElements());
        }};
        return map;
    }

    public static Map<String,Object> toPage(Object object, Object totalElements) {
        Map<String, Object> map = new LinkedHashMap<String, Object>(CommonConstant.MAP_INIT_CAPACITY) {{
            put("content", object);
            put("totalElements", totalElements);
        }};
        return map;
    }
}
