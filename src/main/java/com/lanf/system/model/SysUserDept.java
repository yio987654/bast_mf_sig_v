package com.lanf.system.model;

import com.lanf.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 用户和部门中间表 po类
 * @date 2020-04-20 13:13:24
 */
@Data
@ApiModel(description = "用户和部门中间表")
@TableName("sys_user_dept")
public class SysUserDept extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "用户id")
    @TableField("user_id")
    private String userId;
    @ApiModelProperty(value = "部门id")
    @TableField("dept_id")
    private String deptId;
    @ApiModelProperty(value = "用户名")
    @TableField(exist = false)
    private String userName;
}
