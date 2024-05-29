package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生树形关系表 vo类
 * @date 2023-11-23 16:57:09
 */
@Data
public class TbStudentTreeRelQueryVo {
    private String studentId;
    private String treesId;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

