package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试2 vo类
 * @date 2023-11-24 00:28:41
 */
@Data
public class TbTreeTest2QueryVo {
    private String name;
    private String parentId;
    private Integer sortValue;
    private String studentId;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

