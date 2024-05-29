package ${packageName}.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
/**
* @author ${author}
* @version 1.0
* @description ${tableRemark} 导出类
* @date ${createTime}
*/
@Data
@ApiModel(description = "${tableRemark}")
<#if genType=='1'>
public class ${modelName}ExportVo  {
<#else>
public class ${modelName}ExportVo  {
</#if>
<#list data as var>
<#if var.columeName!='id' && var.isContainId!='true'   &&  var.columeName!='create_time' && var.columeName!='update_time' && var.columeName!='is_deleted'  >
      @ApiModelProperty(value = "${var.remarks}")
      @ExcelProperty("${var.remarks}")
      private ${var.typeName} ${var.attrName};
   <#if var.mod!='default' && var.attrName!='deptId' >
      @ExcelProperty("${var.remarks}")
      private ${var.typeName} ${var.modValueAlias};
   </#if>
</#if>
   </#list>
   <#if isGenDept=='true'>
      @ApiModelProperty(value = "部门")
      @ExcelProperty("部门")
      private String deptName;
   </#if>
   }
