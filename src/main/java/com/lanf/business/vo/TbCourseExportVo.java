package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 课程表 导出类
 * @date 2023-11-24 00:26:45
 */
@Data
@ApiModel(description = "课程表")
public class TbCourseExportVo {
    @ApiModelProperty(value = "课程名称")
    @ExcelProperty("课程名称")
    private String courseName;
}
