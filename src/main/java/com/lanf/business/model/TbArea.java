package com.lanf.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanf.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 地区 po类
 * @date 2023-11-23 16:56:37
 */
@Data
@ApiModel(description = "地区")
@TableName("tb_area")
public class TbArea extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;
}
