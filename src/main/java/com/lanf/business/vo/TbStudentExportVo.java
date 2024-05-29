package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生 导出类
 * @date 2023-11-24 00:02:09
 */
@Data
@ApiModel(description = "学生")
public class TbStudentExportVo {
    @ApiModelProperty(value = "学生姓名")
    @ExcelProperty("学生姓名")
    private String name;
    @ApiModelProperty(value = "部门")
    @ExcelProperty("部门")
    private String deptName;
}
