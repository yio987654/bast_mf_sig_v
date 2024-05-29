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
 * @description 学生选课程关系表 po类
 * @date 2023-11-23 16:56:56
 */
@Data
@ApiModel(description = "学生选课程关系表")
@TableName("tb_student_course")
public class TbStudentCourse extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "学生id")
    @TableField("student_id")
    private String studentId;
    @ApiModelProperty(value = "课程id")
    @TableField("course_id")
    private String courseId;
}
