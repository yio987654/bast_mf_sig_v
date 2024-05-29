package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生选课程关系表 vo类
 * @date 2023-11-23 16:56:56
 */
@Data
public class TbStudentCourseQueryVo {
    private String studentId;
    private String courseId;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

