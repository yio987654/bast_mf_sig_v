package com.lanf.generator.form.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.generator.form.model.SysForm;
import com.lanf.generator.form.vo.SysFormQueryVo;
import com.lanf.generator.form.vo.SysFormExportVo;
import com.lanf.generator.form.service.SysFormService;
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

/**
 * @author tanlingfei
 * @version 1.0
 * @description 表单
 * @date 2023-10-06 22:40:26
 */
@Api(tags = "表单")
@RestController
@RequestMapping("/generator/sysForm")
public class SysFormController {
    @Autowired
    private SysFormService sysFormService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.sysForm.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "sysFormQueryVo", value = "查询对象", required = false)
                        SysFormQueryVo sysFormQueryVo) {
        Page<SysForm> pageParam = new Page<>(page, limit);
        IPage<SysForm> pageModel = sysFormService.selectPage(pageParam, sysFormQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.sysForm.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "sysFormQueryVo", value = "查询对象", required = false)
                       SysFormQueryVo sysFormQueryVo) {
        List<SysForm> list = sysFormService.queryList(sysFormQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysForm.list')")
    @ApiOperation(value = "所有表单列表")
    @GetMapping("findAll")
    public Result findAllSysForm() {
        //调用service的方法实现查询所有的操作
        List<SysForm> list = sysFormService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysForm.list')")
    @ApiOperation(value = "获取表单")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        SysForm sysForm = sysFormService.getById(id);
        return Result.ok(sysForm);
    }

    @PreAuthorize("hasAuthority('bnt.sysForm.list')")
    @ApiOperation(value = "获取表单集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<SysForm> list = sysFormService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "表单", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysForm.add')")
    @ApiOperation(value = "保存表单")
    @PostMapping("/save")
    public Result save(@RequestBody SysForm sysForm) {
        sysFormService.save(sysForm);
        return Result.ok();
    }

    @Log(title = "表单", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysForm.update')")
    @ApiOperation(value = "更新表单")
    @PutMapping("/update")
    public Result updateById(@RequestBody SysForm sysForm) {
        sysFormService.updateById(sysForm);
        return Result.ok();
    }

    /**
     * @PreAuthorize("hasAuthority('bnt.sysForm.update')")
     * @ApiOperation(value = "更新状态")
     * @GetMapping("updateStatus/{id}/{status}") public Result updateStatus(@PathVariable String id, @PathVariable String status) {
     * sysFormService.updateStatus(id, status);
     * return Result.ok();
     * }
     **/

    @Log(title = "表单", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysForm.remove')")
    @ApiOperation(value = "删除表单")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        sysFormService.removeById(id);
        return Result.ok();
    }

    @Log(title = "表单", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysForm.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = sysFormService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "表单", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.sysForm.list')")
    @ApiOperation(value = "导出表单")
    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) {
        List<SysForm> list = this.sysFormService.list();
        List<SysFormExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            SysFormExportVo vo = new SysFormExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, SysFormExportVo.class, "表单数据", "表单数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
