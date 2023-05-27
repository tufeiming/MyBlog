package com.kafka.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils() {
    }

    public static <T1, T2> T2 copyBean(T1 source, Class<T2> clazz) {
        // 创建目标对象
        T2 result = null;
        try {
            result = clazz.getDeclaredConstructor().newInstance();
            // 实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    public static <T1, T2> List<T2> copyBeanList(List<T1> sourceList, Class<T2> clazz) {
        return sourceList.stream()
                .map(e -> copyBean(e, clazz))
                .collect(Collectors.toList());
    }
}
