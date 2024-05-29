package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 班级 vo类
 * @date 2023-11-23 16:56:22
 */
@Data
public class TbClassQueryVo {
    private String name;
    private String remark;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

