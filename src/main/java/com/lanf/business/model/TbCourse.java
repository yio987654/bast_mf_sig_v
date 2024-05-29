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
 * @description 课程表 po类
 * @date 2023-11-24 00:26:45
 */
@Data
@ApiModel(description = "课程表")
@TableName("tb_course")
public class TbCourse extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "课程名称")
    @TableField("course_name")
    private String courseName;
    @TableField(exist = false)
    private List<String> studentIdList;
}
