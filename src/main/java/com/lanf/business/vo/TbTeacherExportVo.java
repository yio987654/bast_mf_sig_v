package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 讲师 导出类
 * @date 2023-11-24 00:27:34
 */
@Data
@ApiModel(description = "讲师")
public class TbTeacherExportVo {
    @ApiModelProperty(value = "讲师名称")
    @ExcelProperty("讲师名称")
    private String teachName;
}
