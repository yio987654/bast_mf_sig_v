package com.lanf.business.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试 vo类
 * @date 2023-11-23 16:57:00
 */
@Data
public class TbTreeTestQueryVo {
    private String name;
    private String treeRemark;
    private String parentId;
    private Integer sortValue;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

