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
 * @description 表单选项 po类
 * @date 2023-10-08 10:50:46
 */
@Data
@ApiModel(description = "表单选项")
@TableName("sys_form_item")
public class SysFormItem extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "")
    @TableField("form_id")
    private String formId;
    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "                                                                                ")
    @TableField("description")
    private String description;
    @ApiModelProperty(value = "字段类型")
    @TableField("item_type")
    private String itemType;
    @TableField("is_allow_null")
    private String isAllowNull;
    @TableField(exist = false)
    private String typeName;
    @TableField(exist = false)
    private String isAllow;
}
