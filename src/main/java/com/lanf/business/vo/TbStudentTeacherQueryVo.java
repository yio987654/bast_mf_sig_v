package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生讲师关系表 vo类
 * @date 2023-11-23 16:56:48
 */
@Data
public class TbStudentTeacherQueryVo {
    private String studentId;
    private String teacherId;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

