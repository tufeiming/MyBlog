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
 * 友链(Link)表实体类
 *
 * @author Kafka
 * @since 2023-05-25 21:04:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("link")
public class Link {
    @TableId
    private Long id;
    private String name;
    private String logo;
    private String description;
    // 网站地址
    private String address;
    // 审核状态 (0代表审核通过，1代表审核未通过，2代表未审核)
    private String status;
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    // 删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
}
