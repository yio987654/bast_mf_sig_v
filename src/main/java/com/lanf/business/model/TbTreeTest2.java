package com.lanf.business.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lanf.model.base.TreeEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试2 po类
 * @date 2023-11-24 00:28:41
 */
@Data
@ApiModel(description = "树测试2")
@TableName("tb_tree_test2")
public class TbTreeTest2 extends TreeEntity {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value = "名称")
    @TableField("name")
    private String name;
    @ApiModelProperty(value = "上级")
    @TableField("parent_id")
    private String parentId;
    @ApiModelProperty(value = "排序")
    @TableField("sort_value")
    private Integer sortValue;
    @TableField(exist = false)
    private List<String> studentIdList;
    @TableField(exist = false)
    private String parentName;
}
