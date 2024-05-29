package ${packageName}.model;

import com.lanf.model.base.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
<#if genType=='1'>
import com.lanf.model.base.TreeEntity;
</#if>
/**
* @author ${author}
* @version 1.0
* @description ${tableRemark} po类
* @date ${createTime}
*/
@Data
@ApiModel(description = "${tableRemark}")
@TableName("${tableName}")
<#if genType=='1'>
public class ${modelName} extends TreeEntity {
<#else>
public class ${modelName} extends BaseEntity {
</#if>
        private static final long serialVersionUID = 1L;
<#list data as var>
    <#if var.columeName!='id' && var.columeName!='create_time' && var.columeName!='update_time' && var.columeName!='is_deleted'  >
        @ApiModelProperty(value = "${var.remarks}")
        @TableField("${var.columeName}")
        private ${var.typeName} ${var.attrName};
        <#if var.mod!='default' && var.attrName!='deptId' >
        @TableField(exist = false)
        private ${var.typeName} ${var.modValueAlias};
        </#if>
    </#if>
       <#if var.isMul=='true' && var.attrName=='id' && var.mod!='default' && var.mod!='radio'>
      <#list var.mulTableVoList as vl>
        @TableField(exist = false)
        private List<String> ${vl.mulSecColum}List;
      </#list>
       </#if>
    <#if  var.attrName=='id'>
    <#list var.mulTableVoList as vl >
        <#if vl.modTable=='sys_dic_item'>
        @TableField(exist = false)
        private String  dicCode${var_index}s;
        </#if>
    </#list>
    </#if>
</#list>
<#if isGenDept=='true'>
        @TableField(exist = false)
        private String deptName;
</#if>
<#if genType=='1'>
        @TableField(exist = false)
        private String parentName;
</#if>
}
