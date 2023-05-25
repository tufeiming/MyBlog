package com.kafka.util;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtil {
    private BeanCopyUtil() {
    }

    public static <T, V> V copyBean(T source, Class<V> clazz) {
        // 创建目标对象
        V result = null;
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

    public static <T, V> List<V> copyBeanList(List<T> sourceList, Class<V> clazz) {
        return sourceList.stream()
                .map(e -> copyBean(e, clazz))
                .collect(Collectors.toList());
    }
}
