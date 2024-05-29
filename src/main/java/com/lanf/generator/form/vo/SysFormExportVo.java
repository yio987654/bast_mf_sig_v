package com.lanf.generator.form.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单 导出类
 * @date 2023-10-06 22:40:26
 */
@Data
@ApiModel(description = "表单")
public class SysFormExportVo {
    @ApiModelProperty(value = "名称")
    @ExcelProperty("名称")
    private String name;
    @ApiModelProperty(value = "表单描述")
    @ExcelProperty("表单描述")
    private String description;
}
