package com.lanf.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanf.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生树形关系表 po类
 * @date 2023-11-23 16:57:09
 */
@Data
@ApiModel(description = "学生树形关系表")
@TableName("tb_student_tree_rel")
public class TbStudentTreeRel extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "学生id")
    @TableField("student_id")
    private String studentId;
    @ApiModelProperty(value = "树id")
    @TableField("trees_id")
    private String treesId;
}
