package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbCourse;
import com.lanf.business.service.TbCourseService;
import com.lanf.business.vo.TbCourseQueryVo;
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
 * @description 课程表
 * @date 2023-11-24 00:26:45
 */
@Api(tags = "课程表")
@RestController
@RequestMapping("/business/tbCourse")
public class TbCourseController {
    @Autowired
    private TbCourseService tbCourseService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbCourse.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbCourseQueryVo", value = "查询对象", required = false)
                        TbCourseQueryVo tbCourseQueryVo) {
        Page<TbCourse> pageParam = new Page<>(page, limit);
        IPage<TbCourse> pageModel = tbCourseService.selectPage(pageParam, tbCourseQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbCourse.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbCourseQueryVo", value = "查询对象", required = false)
                       TbCourseQueryVo tbCourseQueryVo) {
        List<TbCourse> list = tbCourseService.queryList(tbCourseQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbCourse.list')")
    @ApiOperation(value = "所有课程表列表")
    @GetMapping("findAll")
    public Result findAllTbCourse() {
        //调用service的方法实现查询所有的操作
        List<TbCourse> list = tbCourseService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbCourse.list')")
    @ApiOperation(value = "获取课程表")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbCourse tbCourse = tbCourseService.getById(id);
        return Result.ok(tbCourse);
    }

    @PreAuthorize("hasAuthority('bnt.tbCourse.list')")
    @ApiOperation(value = "获取课程表集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbCourse> list = tbCourseService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "课程表", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbCourse.add')")
    @ApiOperation(value = "保存课程表")
    @PostMapping("/save")
    public Result save(@RequestBody TbCourse tbCourse) {
        tbCourseService.save(tbCourse);
        return Result.ok();
    }

    @Log(title = "课程表", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbCourse.update')")
    @ApiOperation(value = "更新课程表")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbCourse tbCourse) {
        tbCourseService.updateById(tbCourse);
        return Result.ok();
    }

    @Log(title = "课程表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbCourse.remove')")
    @ApiOperation(value = "删除课程表")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbCourseService.removeById(id);
        return Result.ok();
    }

    @Log(title = "课程表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbCourse.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbCourseService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "课程表", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbCourse.list')")
    @ApiOperation(value = "导出课程表")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbCourseService.exportData(response);
    }
}
