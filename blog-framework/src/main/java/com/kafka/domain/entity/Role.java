package com.kafka.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * 角色信息表(Role)表实体类
 *
 * @author Kafka
 * @since 2023-06-06 22:46:06
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("role")
public class Role  {
    // 角色ID
    @TableId
    private Long id;
    // 角色名称
    private String roleName;
    // 角色权限字符串
    private String roleKey;
    // 显示顺序
    private Integer roleSort;
    // 角色状态（0正常 1停用）
    private String status;
    // 删除标志（0代表存在 1代表删除）
    private String delFlag;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 备注
    private String remark;
}
