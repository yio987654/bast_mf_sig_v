package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 班级 导出类
 * @date 2023-11-23 16:56:22
 */
@Data
@ApiModel(description = "班级")
public class TbClassExportVo {
    @ApiModelProperty(value = "名称")
    @ExcelProperty("名称")
    private String name;
    @ApiModelProperty(value = "备注")
    @ExcelProperty("备注")
    private String remark;
}
