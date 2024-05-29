package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 地区 vo类
 * @date 2023-11-23 16:56:37
 */
@Data
public class TbAreaQueryVo {
    private String name;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

