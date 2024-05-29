package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbClass;
import com.lanf.business.service.TbClassService;
import com.lanf.business.vo.TbClassQueryVo;
import com.lanf.common.result.Result;
import com.lanf.log.annotation.Log;
import com.lanf.log.type.BusinessType;
import com.lanf.system.easyexcel.ExcelHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 班级
 * @date 2023-11-23 16:56:22
 */
@Api(tags = "班级")
@RestController
@RequestMapping("/business/tbClass")
public class TbClassController {
    @Autowired
    private TbClassService tbClassService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbClass.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbClassQueryVo", value = "查询对象", required = false)
                        TbClassQueryVo tbClassQueryVo) {
        Page<TbClass> pageParam = new Page<>(page, limit);
        IPage<TbClass> pageModel = tbClassService.selectPage(pageParam, tbClassQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbClass.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbClassQueryVo", value = "查询对象", required = false)
                       TbClassQueryVo tbClassQueryVo) {
        List<TbClass> list = tbClassService.queryList(tbClassQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbClass.list')")
    @ApiOperation(value = "所有班级列表")
    @GetMapping("findAll")
    public Result findAllTbClass() {
        //调用service的方法实现查询所有的操作
        List<TbClass> list = tbClassService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbClass.list')")
    @ApiOperation(value = "获取班级")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbClass tbClass = tbClassService.getById(id);
        return Result.ok(tbClass);
    }

    @PreAuthorize("hasAuthority('bnt.tbClass.list')")
    @ApiOperation(value = "获取班级集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbClass> list = tbClassService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "班级", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbClass.add')")
    @ApiOperation(value = "保存班级")
    @PostMapping("/save")
    public Result save(@RequestBody TbClass tbClass) {
        tbClassService.save(tbClass);
        return Result.ok();
    }

    @Log(title = "班级", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbClass.update')")
    @ApiOperation(value = "更新班级")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbClass tbClass) {
        tbClassService.updateById(tbClass);
        return Result.ok();
    }

    @Log(title = "班级", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbClass.remove')")
    @ApiOperation(value = "删除班级")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbClassService.removeById(id);
        return Result.ok();
    }

    @Log(title = "班级", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbClass.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbClassService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "班级", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbClass.list')")
    @ApiOperation(value = "导出班级")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbClassService.exportData(response);
    }
}
