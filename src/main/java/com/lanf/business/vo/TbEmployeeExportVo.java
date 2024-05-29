package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
/**
* @author generator
* @version 1.0
* @description 员工信息 导出类
* @date 2023-11-30 10:44:29
*/
@Data
@ApiModel(description = "员工信息")
public class TbEmployeeExportVo  {
      @ApiModelProperty(value = "姓名")
      @ExcelProperty("姓名")
      private String realname;
      @ApiModelProperty(value = "年龄")
      @ExcelProperty("年龄")
      private Integer age;
      @ApiModelProperty(value = "出生日期")
      @ExcelProperty("出生日期")
      private java.util.Date bornDate;
      @ApiModelProperty(value = "部门")
      @ExcelProperty("部门")
      private String deptName;
   }
