package com.lanf.generator.form.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单选项 vo类
 * @date 2023-10-08 10:50:46
 */
@Data
public class SysFormItemQueryVo {
    private String formId;
    private String tabName;
    private String name;
    private String description;
    private String itemType;
    private Date createTimeBegin;
    private Date createTimeEnd;
    private Date updateTimeBegin;
    private Date updateTimeEnd;
}

