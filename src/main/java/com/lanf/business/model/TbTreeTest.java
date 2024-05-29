package com.lanf.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanf.model.base.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试 po类
 * @date 2023-11-23 16:57:00
 */
@Data
@ApiModel(description = "树测试")
@TableName("tb_tree_test")
public class TbTreeTest extends TreeEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "备注")
    @TableField("tree_remark")
    private String treeRemark;
    @ApiModelProperty(value = "上级")
    @TableField("parent_id")
    private String parentId;
    @ApiModelProperty(value = "排序")
    @TableField("sort_value")
    private Integer sortValue;
    @TableField(exist = false)
    private String parentName;
}
