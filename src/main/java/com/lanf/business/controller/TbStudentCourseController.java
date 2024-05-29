package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudentCourse;
import com.lanf.business.service.TbStudentCourseService;
import com.lanf.business.vo.TbStudentCourseQueryVo;
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
 * @description 学生选课程关系表
 * @date 2023-11-23 16:56:56
 */
@Api(tags = "学生选课程关系表")
@RestController
@RequestMapping("/business/tbStudentCourse")
public class TbStudentCourseController {
    @Autowired
    private TbStudentCourseService tbStudentCourseService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbStudentCourseQueryVo", value = "查询对象", required = false)
                        TbStudentCourseQueryVo tbStudentCourseQueryVo) {
        Page<TbStudentCourse> pageParam = new Page<>(page, limit);
        IPage<TbStudentCourse> pageModel = tbStudentCourseService.selectPage(pageParam, tbStudentCourseQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbStudentCourseQueryVo", value = "查询对象", required = false)
                       TbStudentCourseQueryVo tbStudentCourseQueryVo) {
        List<TbStudentCourse> list = tbStudentCourseService.queryList(tbStudentCourseQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.list')")
    @ApiOperation(value = "所有学生选课程关系表列表")
    @GetMapping("findAll")
    public Result findAllTbStudentCourse() {
        //调用service的方法实现查询所有的操作
        List<TbStudentCourse> list = tbStudentCourseService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.list')")
    @ApiOperation(value = "获取学生选课程关系表")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbStudentCourse tbStudentCourse = tbStudentCourseService.getById(id);
        return Result.ok(tbStudentCourse);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.list')")
    @ApiOperation(value = "获取学生选课程关系表集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbStudentCourse> list = tbStudentCourseService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "学生选课程关系表", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.add')")
    @ApiOperation(value = "保存学生选课程关系表")
    @PostMapping("/save")
    public Result save(@RequestBody TbStudentCourse tbStudentCourse) {
        tbStudentCourseService.save(tbStudentCourse);
        return Result.ok();
    }

    @Log(title = "学生选课程关系表", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.update')")
    @ApiOperation(value = "更新学生选课程关系表")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbStudentCourse tbStudentCourse) {
        tbStudentCourseService.updateById(tbStudentCourse);
        return Result.ok();
    }

    @Log(title = "学生选课程关系表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.remove')")
    @ApiOperation(value = "删除学生选课程关系表")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbStudentCourseService.removeById(id);
        return Result.ok();
    }

    @Log(title = "学生选课程关系表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbStudentCourseService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "学生选课程关系表", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbStudentCourse.list')")
    @ApiOperation(value = "导出学生选课程关系表")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbStudentCourseService.exportData(response);
    }
}
