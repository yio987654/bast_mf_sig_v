package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudentTeacher;
import com.lanf.business.service.TbStudentTeacherService;
import com.lanf.business.vo.TbStudentTeacherQueryVo;
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
 * @description 学生讲师关系表
 * @date 2023-11-23 16:56:48
 */
@Api(tags = "学生讲师关系表")
@RestController
@RequestMapping("/business/tbStudentTeacher")
public class TbStudentTeacherController {
    @Autowired
    private TbStudentTeacherService tbStudentTeacherService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbStudentTeacherQueryVo", value = "查询对象", required = false)
                        TbStudentTeacherQueryVo tbStudentTeacherQueryVo) {
        Page<TbStudentTeacher> pageParam = new Page<>(page, limit);
        IPage<TbStudentTeacher> pageModel = tbStudentTeacherService.selectPage(pageParam, tbStudentTeacherQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbStudentTeacherQueryVo", value = "查询对象", required = false)
                       TbStudentTeacherQueryVo tbStudentTeacherQueryVo) {
        List<TbStudentTeacher> list = tbStudentTeacherService.queryList(tbStudentTeacherQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.list')")
    @ApiOperation(value = "所有学生讲师关系表列表")
    @GetMapping("findAll")
    public Result findAllTbStudentTeacher() {
        //调用service的方法实现查询所有的操作
        List<TbStudentTeacher> list = tbStudentTeacherService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.list')")
    @ApiOperation(value = "获取学生讲师关系表")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbStudentTeacher tbStudentTeacher = tbStudentTeacherService.getById(id);
        return Result.ok(tbStudentTeacher);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.list')")
    @ApiOperation(value = "获取学生讲师关系表集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbStudentTeacher> list = tbStudentTeacherService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "学生讲师关系表", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.add')")
    @ApiOperation(value = "保存学生讲师关系表")
    @PostMapping("/save")
    public Result save(@RequestBody TbStudentTeacher tbStudentTeacher) {
        tbStudentTeacherService.save(tbStudentTeacher);
        return Result.ok();
    }

    @Log(title = "学生讲师关系表", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.update')")
    @ApiOperation(value = "更新学生讲师关系表")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbStudentTeacher tbStudentTeacher) {
        tbStudentTeacherService.updateById(tbStudentTeacher);
        return Result.ok();
    }

    @Log(title = "学生讲师关系表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.remove')")
    @ApiOperation(value = "删除学生讲师关系表")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbStudentTeacherService.removeById(id);
        return Result.ok();
    }

    @Log(title = "学生讲师关系表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbStudentTeacherService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "学生讲师关系表", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbStudentTeacher.list')")
    @ApiOperation(value = "导出学生讲师关系表")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbStudentTeacherService.exportData(response);
    }
}
