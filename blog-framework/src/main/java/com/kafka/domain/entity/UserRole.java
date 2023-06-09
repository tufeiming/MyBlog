package com.kafka.domain.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * 用户和角色关联表(UserRole)表实体类
 *
 * @author Kafka
 * @since 2023-06-09 15:25:57
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user_role")
public class UserRole  {
    // 用户ID
    private Long userId;
    // 角色ID
    private Long roleId;
}
