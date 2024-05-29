package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudentTreeRel;
import com.lanf.business.service.TbStudentTreeRelService;
import com.lanf.business.vo.TbStudentTreeRelQueryVo;
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
 * @description 学生树形关系表
 * @date 2023-11-23 16:57:09
 */
@Api(tags = "学生树形关系表")
@RestController
@RequestMapping("/business/tbStudentTreeRel")
public class TbStudentTreeRelController {
    @Autowired
    private TbStudentTreeRelService tbStudentTreeRelService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbStudentTreeRelQueryVo", value = "查询对象", required = false)
                        TbStudentTreeRelQueryVo tbStudentTreeRelQueryVo) {
        Page<TbStudentTreeRel> pageParam = new Page<>(page, limit);
        IPage<TbStudentTreeRel> pageModel = tbStudentTreeRelService.selectPage(pageParam, tbStudentTreeRelQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbStudentTreeRelQueryVo", value = "查询对象", required = false)
                       TbStudentTreeRelQueryVo tbStudentTreeRelQueryVo) {
        List<TbStudentTreeRel> list = tbStudentTreeRelService.queryList(tbStudentTreeRelQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.list')")
    @ApiOperation(value = "所有学生树形关系表列表")
    @GetMapping("findAll")
    public Result findAllTbStudentTreeRel() {
        //调用service的方法实现查询所有的操作
        List<TbStudentTreeRel> list = tbStudentTreeRelService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.list')")
    @ApiOperation(value = "获取学生树形关系表")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbStudentTreeRel tbStudentTreeRel = tbStudentTreeRelService.getById(id);
        return Result.ok(tbStudentTreeRel);
    }

    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.list')")
    @ApiOperation(value = "获取学生树形关系表集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbStudentTreeRel> list = tbStudentTreeRelService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "学生树形关系表", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.add')")
    @ApiOperation(value = "保存学生树形关系表")
    @PostMapping("/save")
    public Result save(@RequestBody TbStudentTreeRel tbStudentTreeRel) {
        tbStudentTreeRelService.save(tbStudentTreeRel);
        return Result.ok();
    }

    @Log(title = "学生树形关系表", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.update')")
    @ApiOperation(value = "更新学生树形关系表")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbStudentTreeRel tbStudentTreeRel) {
        tbStudentTreeRelService.updateById(tbStudentTreeRel);
        return Result.ok();
    }

    @Log(title = "学生树形关系表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.remove')")
    @ApiOperation(value = "删除学生树形关系表")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbStudentTreeRelService.removeById(id);
        return Result.ok();
    }

    @Log(title = "学生树形关系表", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbStudentTreeRelService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "学生树形关系表", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbStudentTreeRel.list')")
    @ApiOperation(value = "导出学生树形关系表")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbStudentTreeRelService.exportData(response);
    }
}
