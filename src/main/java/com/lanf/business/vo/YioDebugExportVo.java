package com.lanf.business.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description yio测试 导出类
* @date 2024-05-09 23:38:22
*/
@Data
@ApiModel(description = "yio测试")
public class YioDebugExportVo  {
      @ApiModelProperty(value = "名称")
      @ExcelProperty("名称")
      private String name;
      @ApiModelProperty(value = "邮箱")
      @ExcelProperty("邮箱")
      private String mail;
   }
