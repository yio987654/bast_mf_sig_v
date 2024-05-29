package ${packageName}.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import ${packageName}.model.${modelName};
import ${packageName}.vo.${modelName}QueryVo;
import ${packageName}.vo.${modelName}ExportVo;
import ${packageName}.service.${modelName}Service;
import com.lanf.log.type.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.lanf.log.annotation.Log;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;
import com.lanf.system.easyexcel.ExcelHandler;
import com.lanf.system.exception.LanfException;
import javax.annotation.Resource;
<#if genType=='1'>
import com.lanf.model.base.TreeEntity;
</#if>
/**
* @author ${author}
* @version 1.0
* @description ${tableRemark}
* @date ${createTime}
*/
@Api(tags = "${tableRemark}")
@RestController
@RequestMapping("/${packageSub}/${modelName2}")
public class ${modelName}Controller {
    @Autowired
    private ${modelName}Service ${modelName2}Service;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.${modelName2}.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
    @PathVariable Long page,
    @ApiParam(name = "limit", value = "每页记录数", required = true)
    @PathVariable Long limit,
    @ApiParam(name = "${modelName2}QueryVo", value = "查询对象", required = false)
    ${modelName}QueryVo ${modelName2}QueryVo) {
        Page<${modelName}> pageParam = new Page<>(page, limit);
        IPage<${modelName}> pageModel = ${modelName2}Service.selectPage(pageParam, ${modelName2}QueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.${modelName2}.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "${modelName2}QueryVo", value = "查询对象", required = false)
          ${modelName}QueryVo ${modelName2}QueryVo) {
        <#if genType=='1'>
          List<TreeEntity> list = ${modelName2}Service.queryList(${modelName2}QueryVo);
        <#else>
          List<${modelName}> list = ${modelName2}Service.queryList(${modelName2}QueryVo);
        </#if>
          return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.${modelName2}.list')")
    @ApiOperation(value = "所有${tableRemark}列表")
    @GetMapping("findAll")
    public Result findAll${modelName}() {
        //调用service的方法实现查询所有的操作
        List<${modelName}> list = ${modelName2}Service.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.${modelName2}.list')")
    @ApiOperation(value = "获取${tableRemark}")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        ${modelName} ${modelName2} = ${modelName2}Service.getById(id);
    <#if genType=='1'>
        ${modelName} parent = ${modelName2}Service.getById(${modelName2}.getParentId());
        if (parent != null) {
           ${modelName2}.setParentName(parent.getName());
           ${modelName2}.setParentId(parent.getId());
        }
    </#if>
        return Result.ok(${modelName2});
    }

    @PreAuthorize("hasAuthority('bnt.${modelName2}.list')")
    @ApiOperation(value = "获取${tableRemark}集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<${modelName}> list = ${modelName2}Service.getByIds(idList);
        return Result.ok(list);
     }

    @Log(title = "${tableRemark}", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.${modelName2}.add')")
    @ApiOperation(value = "保存${tableRemark}")
    @PostMapping("/save")
    public Result save(@RequestBody ${modelName} ${modelName2}) {
        ${modelName2}Service.save(${modelName2});
        return Result.ok();
    }

    @Log(title = "${tableRemark}", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.${modelName2}.update')")
    @ApiOperation(value = "更新${tableRemark}")
    @PutMapping("/update")
    public Result updateById(@RequestBody ${modelName} ${modelName2}) {
        ${modelName2}Service.updateById(${modelName2});
        return Result.ok();
    }

    @Log(title = "${tableRemark}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.${modelName2}.remove')")
    @ApiOperation(value = "删除${tableRemark}")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        ${modelName2}Service.removeById(id);
        return Result.ok();
    }

    @Log(title = "${tableRemark}", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.${modelName2}.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = ${modelName2}Service.removeByIds(idList);
        if (b) {
          return Result.ok();
        }else{
          return Result.fail();
        }
    }
<#if genType!='1'>
    @Log(title = "${tableRemark}", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.${modelName2}.list')")
    @ApiOperation(value = "导出${tableRemark}")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response)  {
        this.${modelName2}Service.exportData(response);
    }
</#if>
}
