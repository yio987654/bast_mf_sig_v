package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudent;
import com.lanf.business.service.TbStudentService;
import com.lanf.business.vo.TbStudentQueryVo;
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
 * @description 学生
 * @date 2023-11-24 00:02:09
 */
@Api(tags = "学生")
@RestController
@RequestMapping("/business/tbStudent")
public class TbStudentController {
    @Autowired
    private TbStudentService tbStudentService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbStudent.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbStudentQueryVo", value = "查询对象", required = false)
                        TbStudentQueryVo tbStudentQueryVo) {
        Page<TbStudent> pageParam = new Page<>(page, limit);
        IPage<TbStudent> pageModel = tbStudentService.selectPage(pageParam, tbStudentQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudent.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbStudentQueryVo", value = "查询对象", required = false)
                       TbStudentQueryVo tbStudentQueryVo) {
        List<TbStudent> list = tbStudentService.queryList(tbStudentQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudent.list')")
    @ApiOperation(value = "所有学生列表")
    @GetMapping("findAll")
    public Result findAllTbStudent() {
        //调用service的方法实现查询所有的操作
        List<TbStudent> list = tbStudentService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudent.list')")
    @ApiOperation(value = "获取学生")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbStudent tbStudent = tbStudentService.getById(id);
        return Result.ok(tbStudent);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudent.list')")
    @ApiOperation(value = "获取学生集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbStudent> list = tbStudentService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "学生", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbStudent.add')")
    @ApiOperation(value = "保存学生")
    @PostMapping("/save")
    public Result save(@RequestBody TbStudent tbStudent) {
        tbStudentService.save(tbStudent);
        return Result.ok();
    }

    @Log(title = "学生", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbStudent.update')")
    @ApiOperation(value = "更新学生")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbStudent tbStudent) {
        tbStudentService.updateById(tbStudent);
        return Result.ok();
    }

    @Log(title = "学生", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudent.remove')")
    @ApiOperation(value = "删除学生")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbStudentService.removeById(id);
        return Result.ok();
    }

    @Log(title = "学生", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudent.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbStudentService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "学生", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbStudent.list')")
    @ApiOperation(value = "导出学生")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbStudentService.exportData(response);
    }
}
