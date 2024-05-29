package com.lanf.generator.form.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单选项 导出类
 * @date 2023-10-08 10:50:46
 */
@Data
@ApiModel(description = "表单选项")
public class SysFormItemExportVo {
    @ApiModelProperty(value = "")
    @ExcelProperty("")
    private String formId;
    @ApiModelProperty(value = "名称")
    @ExcelProperty("名称")
    private String name;
    @ApiModelProperty(value = "                                                                                ")
    @ExcelProperty("                                                                                ")
    private String description;
    @ApiModelProperty(value = "字段类型1:int 2:varchar 3:timestamp 4:text 6:BigDecimal")
    @ExcelProperty("字段类型1:int 2:varchar 3:timestamp 4:text 6:BigDecimal")
    private String itemType;
}
