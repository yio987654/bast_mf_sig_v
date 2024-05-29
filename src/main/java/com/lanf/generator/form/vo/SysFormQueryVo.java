package com.lanf.generator.form.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单 vo类
 * @date 2023-10-06 22:40:26
 */
@Data
public class SysFormQueryVo {
    private String name;
    private String description;
    private String deptId;
    private List<String> curDeptIds;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

