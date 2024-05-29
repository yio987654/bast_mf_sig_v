package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 课程表 vo类
 * @date 2023-11-24 00:26:45
 */
@Data
public class TbCourseQueryVo {
    private String courseName;
    private String studentId;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

