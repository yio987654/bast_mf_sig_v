package com.lanf.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanf.model.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 讲师 po类
 * @date 2023-11-24 00:27:34
 */
@Data
@ApiModel(description = "讲师")
@TableName("tb_teacher")
public class TbTeacher extends BaseEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "讲师名称")
    @TableField("teach_name")
    private String teachName;
    @TableField(exist = false)
    private List<String> studentIdList;
}
