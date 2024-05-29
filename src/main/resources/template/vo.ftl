package ${packageName}.vo;

import lombok.Data;
import java.util.Date;
import java.util.List;

/**
* @author ${author}
* @version 1.0
* @description ${tableRemark} vo类
* @date ${createTime}
*/
@Data
public class ${modelName}QueryVo {
<#list data as var>
    <#if var.columeName!='id' && var.columeName!='create_time' && var.columeName!='update_time' && var.columeName!='is_deleted'  >
        <#if var.attrName=='deptId'>
            <#if isGenLeftDept!='true'>
       private ${var.typeName} ${var.attrName};
       private List<String> curDeptIds;
            </#if>
         <#else>
       private ${var.typeName} ${var.attrName};
        </#if>
        <#if var.mod!='default' && var.attrName!='deptId'>
       private ${var.typeName} ${var.modValueAlias};
        </#if>
    <#else>
        <#if  var.columeName=='id' && var.isMul=='true'>
            <#list var.mulTableVoList as vl >
                <#if vl.mulSecColum!='deptId'>
       private  String ${vl.mulSecColum};
                 <#else>
                     <#if isGenLeftDept!='true'>
       private String ${vl.mulSecColum};
       private List<String> curDeptIds;
                     </#if>
                </#if>
            </#list>
        </#if>
    </#if>
</#list>
<#if isGenDept=='true'>
       private String deptName;
</#if>
<#if isGenLeftDept=='true'>
       private String deptId;
       private List<String> curDeptIds;
</#if>
       private Date createTimeBegin;
       private Date createTimeEnd;
       private Date updateTimeBegin;
       private Date updateTimeEnd;
}

