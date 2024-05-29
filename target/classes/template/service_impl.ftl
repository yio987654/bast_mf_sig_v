package ${packageName}.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import ${packageName}.model.${modelName};
import ${packageName}.vo.${modelName}QueryVo;
import ${packageName}.vo.${modelName}ExportVo;
import ${packageName}.mapper.${modelName}Mapper;
import ${packageName}.service.${modelName}Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.system.exception.LanfException;
import com.lanf.system.easyexcel.ExcelHandler;
<#if isGenDept=='true' || isGenLeftDept=='true'>
import com.lanf.system.utils.UserUtil;
import org.springframework.util.CollectionUtils;
import com.lanf.model.system.SysUser;
</#if>
<#list data as var>
<#if var.isMul=='true' && var.attrName=='id' && var.mod!='default' && var.mod!='radio'>
<#list var.mulTableVoList as vl>
import ${packageName}.service.${vl.mulTable}Service;
import ${packageName}.model.${vl.mulTable};
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.function.Function;
</#list>
</#if>
</#list>
<#if genType=='1'>
import com.lanf.model.base.TreeEntity;
import com.lanf.system.utils.TreeHelper;
import org.springframework.util.CollectionUtils;
import java.io.Serializable;
import javax.servlet.http.HttpServletResponse;
</#if>
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
/**
* @author ${author}
* @version 1.0
* @description ${tableRemark} Service实现类
* @date ${createTime}
*/
@Transactional
@Service
public class ${modelName}ServiceImpl extends ServiceImpl
<${modelName}Mapper, ${modelName}> implements ${modelName}Service {
    @Autowired
    private ${modelName}Mapper ${modelName2}Mapper;
    <#list data as var>
    <#if var.isMul=='true' && var.mod!='default' && var.attrName=='id'>
      <#list var.mulTableVoList as vl>
    @Autowired
    private ${vl.mulTable}Service ${vl.mulTable2}Service;
     </#list>
    </#if>
    </#list>
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<${modelName}> selectPage(Page<${modelName}> pageParam, ${modelName}QueryVo ${modelName2}QueryVo) {
        //QueryWrapper<${modelName}> queryWrapper = new QueryWrapper<>();
        //return ${modelName2}Mapper.selectPage(pageParam,queryWrapper);
        <#if isGenDept=='true' || isGenLeftDept=='true'>
            //只查询当前登录所属部门数据
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            ${modelName2}QueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
             return null;
            }
            ${modelName2}QueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        </#if>
        return ${modelName2}Mapper.selectPage(pageParam,${modelName2}QueryVo);
    }

    @Override
<#if genType=='1'>
    public List<TreeEntity> queryList(${modelName}QueryVo ${modelName2}QueryVo){
<#else>
    public List<${modelName}> queryList(${modelName}QueryVo ${modelName2}QueryVo){
</#if>
     <#if isGenDept=='true' || isGenLeftDept=='true'>
            //只查询当前登录所属部门数据
       SysUser sysUser = UserUtil.getUserInfo();
       if ("1".equals(sysUser.getId())) {
            ${modelName2}QueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
            return null;
        }
            ${modelName2}QueryVo.setCurDeptIds(sysUser.getDeptIds());
       }
      </#if>
     List<${modelName}> result = ${modelName2}Mapper.queryList(${modelName2}QueryVo);
<#if genType=='1'>
     if (CollectionUtils.isEmpty(result)) return null;
    //构建树形数据
     List<TreeEntity> treeList = TreeHelper.buildTree(result);
     return treeList;
<#else>
     return result;
</#if>
   }
    <#if genType=='1'>
    private String getTreePath(${modelName} ${modelName2}) {
        if (${modelName2}.getParentId() == null || "0".equals(${modelName2}.getParentId())) {
          return "," + ${modelName2}.getId() + ",";
        } else {
        ${modelName} ${modelName2}1 = this.getById(${modelName2}.getParentId());
        if (!StringUtils.isEmpty(${modelName2}1.getTreePath())) {
          return ${modelName2}1.getTreePath() + ${modelName2}.getId() + ",";
        }
          return getTreePath(${modelName2}1) + ${modelName2}.getId() + ",";
        }
    }
    </#if>

    @Override
    public boolean save(${modelName} ${modelName2}){
        int result = this.${modelName2}Mapper.insert(${modelName2});
        <#list data as var>
            <#if var.isMul=='true' && var.attrName=='id' && var.mod!='default' && var.mod!='radio'>
                <#list var.mulTableVoList as vl>
        List<String> ${vl.mulSecColum}List = ${modelName2}.get${vl.mulUpperSecColum}List();
        if(${vl.mulSecColum}List!=null && ${vl.mulSecColum}List.size()>0){
           for(String ${vl.mulSecColum}:${vl.mulSecColum}List){
              ${vl.mulTable} ${vl.mulTable2} = new ${vl.mulTable}();
              ${vl.mulTable2}.set${vl.mulUpperMainColum}(${modelName2}.getId());
              ${vl.mulTable2}.set${vl.mulUpperSecColum}(${vl.mulSecColum});
              ${vl.mulTable2}Service.save(${vl.mulTable2});
           }
        }
                </#list>
            </#if>
        </#list>
        <#if genType=='1'>
        if ("0".equals(${modelName2}.getParentId())) {
            ${modelName2}.setLevel(1);
         } else {
            ${modelName} ${modelName2}1 = this.getById(${modelName2}.getParentId());
            ${modelName2}.setLevel(${modelName2}1.getLevel() + 1);
         }
            String treePath = this.getTreePath(${modelName2});
            ${modelName2}.setTreePath(treePath);
            this.${modelName2}Mapper.updateById(${modelName2});
         </#if>
        return result>0;
    }
    @Override
    public boolean updateById(${modelName} ${modelName2}){
<#if genType=='1'>
         if ("0".equals(${modelName2}.getParentId())) {
          ${modelName2}.setLevel(1);
         } else {
          ${modelName} ${modelName2}1 = this.getById(${modelName2}.getParentId());
          ${modelName2}.setLevel(${modelName2}1.getLevel() + 1);
         }
         String treePath = this.getTreePath(${modelName2});
         ${modelName2}.setTreePath(treePath);
</#if>
        int row = this.${modelName2}Mapper.updateById(${modelName2});
        if(row <= 0){
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
         }
        <#list data as var>
         <#if var.isMul=='true' && var.attrName=='id' && var.mod!='default' && var.mod!='radio'>
          <#list var.mulTableVoList as vl>
        QueryWrapper<${vl.mulTable}> ${vl.mulTable2}QueryWrapper = new QueryWrapper<${vl.mulTable}>();
        ${vl.mulTable2}QueryWrapper.eq("${vl.mulMainColumData}",${modelName2}.getId());
        ${vl.mulTable2}Service.remove(${vl.mulTable2}QueryWrapper);
        List<String> ${vl.mulSecColum}List = ${modelName2}.get${vl.mulUpperSecColum}List();
        if(${vl.mulSecColum}List!=null && ${vl.mulSecColum}List.size()>0){
           for(String ${vl.mulSecColum}:${vl.mulSecColum}List){
            ${vl.mulTable} ${vl.mulTable2} = new ${vl.mulTable}();
            ${vl.mulTable2}.set${vl.mulUpperMainColum}(${modelName2}.getId());
            ${vl.mulTable2}.set${vl.mulUpperSecColum}(${vl.mulSecColum});
            ${vl.mulTable2}Service.save(${vl.mulTable2});
           }
         }
           </#list>
          </#if>
         </#list>
         return row>0;
    }
    @Override
    public ${modelName} getById(String id){
        <#if isGenDept=='true' && isGenDeptMul!='true'>
        ${modelName} ${modelName2} = ${modelName2}Mapper.getById(id);
         <#else>
         ${modelName} ${modelName2} = ${modelName2}Mapper.selectById(id);
        </#if>
        <#list data as var>
        <#if var.isMul=='true' && var.attrName=='id' && var.mod!='default' && var.mod!='radio'>
        Function<Object,String> f = (o -> o.toString());
        <#list var.mulTableVoList as vl>
        QueryWrapper<${vl.mulTable}> ${vl.mulTable2}QueryWrapper = new QueryWrapper<${vl.mulTable}>();
        ${vl.mulTable2}QueryWrapper.select("${vl.mulSecColumData}");
        ${vl.mulTable2}QueryWrapper.eq("${vl.mulMainColumData}",${modelName2}.getId());
        List<String> ${vl.mulTable2}List = ${vl.mulTable2}Service.listObjs(${vl.mulTable2}QueryWrapper,f);
        ${modelName2}.set${vl.mulUpperSecColum}List(${vl.mulTable2}List);
       </#list>
        </#if>
        </#list>
         return ${modelName2};
    }
   @Override
   public List<${modelName}> getByIds(List<String> ids) {
      List<${modelName}> list = this.${modelName2}Mapper.selectBatchIds(ids);
      return list;
   }
<#if genType!='1'>
   @Override
   public void exportData(HttpServletResponse response) {
   List<${modelName}> list = this.selectPage(new Page<${modelName}>(1, 10000), new ${modelName}QueryVo()).getRecords();
   List<${modelName}ExportVo> expList = new ArrayList<>();
   list.forEach(po -> {
       ${modelName}ExportVo vo = new ${modelName}ExportVo();
       BeanUtils.copyProperties(po, vo);
          expList.add(vo);
      });
      try {
          this.excelHandler.exportExcel(response, expList, ${modelName}ExportVo.class, "${tableRemark}数据", "${tableRemark}数据");
      } catch (Exception e) {
          e.printStackTrace();
          throw new LanfException(9005, "导出失败");
      }
   }
 </#if>
   <#if genType=='1'>
    @Override
    public boolean removeById(Serializable id) {
           int count = this.count(new QueryWrapper<${modelName}>().eq("parent_id", id));
           if (count > 0) {
                  throw new LanfException(ResultCodeEnum.NODE_ERROR);
           }
           ${modelName2}Mapper.deleteById(id);
           return false;
     }
   </#if>
}
