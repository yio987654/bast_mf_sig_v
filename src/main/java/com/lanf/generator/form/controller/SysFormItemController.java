package com.lanf.generator.form.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.generator.form.model.SysFormItem;
import com.lanf.generator.form.vo.SysFormItemQueryVo;
import com.lanf.generator.form.vo.SysFormItemExportVo;
import com.lanf.generator.form.service.SysFormItemService;
import com.lanf.log.type.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
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

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单选项
 * @date 2023-10-08 10:50:46
 */
@Api(tags = "表单选项")
@RestController
@RequestMapping("/generator/sysFormItem")
public class SysFormItemController {
    @Autowired
    private SysFormItemService sysFormItemService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.sysFormItem.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "sysFormItemQueryVo", value = "查询对象", required = false)
                        SysFormItemQueryVo sysFormItemQueryVo) {
        Page<SysFormItem> pageParam = new Page<>(page, limit);
        IPage<SysFormItem> pageModel = sysFormItemService.selectPage(pageParam, sysFormItemQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.sysFormItem.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "sysFormItemQueryVo", value = "查询对象", required = false)
                       SysFormItemQueryVo sysFormItemQueryVo) {
        List<SysFormItem> list = sysFormItemService.queryList(sysFormItemQueryVo);
        if (!CollectionUtils.isEmpty(list)) {
            SysFormItem fix = new SysFormItem();
            fix.setFormId(sysFormItemQueryVo.getFormId());
            fix.setName("id");
            fix.setDescription("主键");
            list.add(fix);
        }
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysFormItem.list')")
    @ApiOperation(value = "所有表单选项列表")
    @GetMapping("findAll")
    public Result findAllSysFormItem() {
        //调用service的方法实现查询所有的操作
        List<SysFormItem> list = sysFormItemService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysFormItem.list')")
    @ApiOperation(value = "获取表单选项")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        SysFormItem sysFormItem = sysFormItemService.getById(id);
        return Result.ok(sysFormItem);
    }

    @PreAuthorize("hasAuthority('bnt.sysFormItem.list')")
    @ApiOperation(value = "获取表单选项集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<SysFormItem> list = sysFormItemService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "表单选项", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysFormItem.add')")
    @ApiOperation(value = "保存表单选项")
    @PostMapping("/save")
    public Result save(@RequestBody SysFormItem sysFormItem) {
        sysFormItemService.save(sysFormItem);
        return Result.ok();
    }

    @Log(title = "表单选项", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysFormItem.update')")
    @ApiOperation(value = "更新表单选项")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysFormItem sysFormItem) {
        sysFormItemService.updateById(sysFormItem);
        return Result.ok();
    }

    /**
     * @PreAuthorize("hasAuthority('bnt.sysFormItem.update')")
     * @ApiOperation(value = "更新状态")
     * @GetMapping("updateStatus/{id}/{status}") public Result updateStatus(@PathVariable String id, @PathVariable String status) {
     * sysFormItemService.updateStatus(id, status);
     * return Result.ok();
     * }
     **/

    @Log(title = "表单选项", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysFormItem.remove')")
    @ApiOperation(value = "删除表单选项")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        sysFormItemService.removeById(id);
        return Result.ok();
    }

    @Log(title = "表单选项", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysFormItem.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = sysFormItemService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "表单选项", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.sysFormItem.list')")
    @ApiOperation(value = "导出表单选项")
    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) {
        List<SysFormItem> list = this.sysFormItemService.list();
        List<SysFormItemExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            SysFormItemExportVo vo = new SysFormItemExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, SysFormItemExportVo.class, "表单选项数据", "表单选项数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
