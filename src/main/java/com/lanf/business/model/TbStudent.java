package com.lanf.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanf.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生 po类
 * @date 2023-11-24 00:02:09
 */
@Data
@ApiModel(description = "学生")
@TableName("tb_student")
public class TbStudent extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "学生姓名")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "所属班级")
    @TableField("class_id")
    private String classId;
    @TableField(exist = false)
    private String className;
    @ApiModelProperty(value = "所属地区")
    @TableField("area_id")
    private String areaId;
    @TableField(exist = false)
    private String areaName;
    @ApiModelProperty(value = "树id")
    @TableField("tree_id")
    private String treeId;
    @TableField(exist = false)
    private String treeName;
    @ApiModelProperty(value = "部门id")
    @TableField("dept_id")
    private String deptId;
    @TableField(exist = false)
    private List<String> treesIdList;
    @TableField(exist = false)
    private List<String> courseIdList;
    @TableField(exist = false)
    private List<String> teacherIdList;
    @TableField(exist = false)
    private String deptName;
}
