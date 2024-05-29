package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 地区 导出类
 * @date 2023-11-23 16:56:37
 */
@Data
@ApiModel(description = "地区")
public class TbAreaExportVo {
    @ApiModelProperty(value = "名称")
    @ExcelProperty("名称")
    private String name;
}
