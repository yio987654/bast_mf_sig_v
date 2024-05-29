package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 测试表2 导出类
* @date 2024-05-09 23:57:31
*/
@Data
@ApiModel(description = "测试表2")
public class Test02ExportVo  {
      @ApiModelProperty(value = "姓名")
      @ExcelProperty("姓名")
      private String name;
      @ApiModelProperty(value = "年纪")
      @ExcelProperty("年纪")
      private Integer age;
   }
