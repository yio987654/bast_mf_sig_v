package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试2 导出类
 * @date 2023-11-24 00:28:41
 */
@Data
@ApiModel(description = "树测试2")
public class TbTreeTest2ExportVo {
    @ApiModelProperty(value = "名称")
    @ExcelProperty("名称")
    private String name;
    @ApiModelProperty(value = "排序")
    @ExcelProperty("排序")
    private Integer sortValue;
}
