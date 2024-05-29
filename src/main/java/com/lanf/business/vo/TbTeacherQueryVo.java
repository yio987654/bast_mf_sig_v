package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 讲师 vo类
 * @date 2023-11-24 00:27:34
 */
@Data
public class TbTeacherQueryVo {
    private String teachName;
    private String studentId;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

