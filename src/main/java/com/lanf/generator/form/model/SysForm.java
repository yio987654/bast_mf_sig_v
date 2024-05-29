package com.lanf.generator.form.model;

import com.lanf.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单 po类
 * @date 2023-10-06 22:40:26
 */
@Data
@ApiModel(description = "表单")
@TableName("sys_form")
public class SysForm extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "表单描述")
    @TableField("description")
    private String description;
    @ApiModelProperty(value = "表单选项列表")
    @TableField(exist = false)
    private List<SysFormItem> itemList;
    @TableField(exist = false)
    private String isFause;
    @TableField("tab_type")
    private String tabType;
}
