package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbTeacher;
import com.lanf.business.service.TbTeacherService;
import com.lanf.business.vo.TbTeacherQueryVo;
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
 * @description 讲师
 * @date 2023-11-24 00:27:34
 */
@Api(tags = "讲师")
@RestController
@RequestMapping("/business/tbTeacher")
public class TbTeacherController {
    @Autowired
    private TbTeacherService tbTeacherService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbTeacher.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbTeacherQueryVo", value = "查询对象", required = false)
                        TbTeacherQueryVo tbTeacherQueryVo) {
        Page<TbTeacher> pageParam = new Page<>(page, limit);
        IPage<TbTeacher> pageModel = tbTeacherService.selectPage(pageParam, tbTeacherQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbTeacher.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbTeacherQueryVo", value = "查询对象", required = false)
                       TbTeacherQueryVo tbTeacherQueryVo) {
        List<TbTeacher> list = tbTeacherService.queryList(tbTeacherQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbTeacher.list')")
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public Result findAllTbTeacher() {
        //调用service的方法实现查询所有的操作
        List<TbTeacher> list = tbTeacherService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbTeacher.list')")
    @ApiOperation(value = "获取讲师")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbTeacher tbTeacher = tbTeacherService.getById(id);
        return Result.ok(tbTeacher);
    }

    @PreAuthorize("hasAuthority('bnt.tbTeacher.list')")
    @ApiOperation(value = "获取讲师集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbTeacher> list = tbTeacherService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "讲师", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbTeacher.add')")
    @ApiOperation(value = "保存讲师")
    @PostMapping("/save")
    public Result save(@RequestBody TbTeacher tbTeacher) {
        tbTeacherService.save(tbTeacher);
        return Result.ok();
    }

    @Log(title = "讲师", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbTeacher.update')")
    @ApiOperation(value = "更新讲师")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbTeacher tbTeacher) {
        tbTeacherService.updateById(tbTeacher);
        return Result.ok();
    }

    @Log(title = "讲师", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbTeacher.remove')")
    @ApiOperation(value = "删除讲师")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbTeacherService.removeById(id);
        return Result.ok();
    }

    @Log(title = "讲师", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbTeacher.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbTeacherService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "讲师", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbTeacher.list')")
    @ApiOperation(value = "导出讲师")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbTeacherService.exportData(response);
    }
}
