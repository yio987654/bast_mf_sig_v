package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbTreeTest;
import com.lanf.business.service.TbTreeTestService;
import com.lanf.business.vo.TbTreeTestQueryVo;
import com.lanf.common.result.Result;
import com.lanf.log.annotation.Log;
import com.lanf.log.type.BusinessType;
import com.lanf.model.base.TreeEntity;
import com.lanf.system.easyexcel.ExcelHandler;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试
 * @date 2023-11-23 16:57:00
 */
@Api(tags = "树测试")
@RestController
@RequestMapping("/business/tbTreeTest")
public class TbTreeTestController {
    @Autowired
    private TbTreeTestService tbTreeTestService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbTreeTest.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbTreeTestQueryVo", value = "查询对象", required = false)
                        TbTreeTestQueryVo tbTreeTestQueryVo) {
        Page<TbTreeTest> pageParam = new Page<>(page, limit);
        IPage<TbTreeTest> pageModel = tbTreeTestService.selectPage(pageParam, tbTreeTestQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbTreeTestQueryVo", value = "查询对象", required = false)
                       TbTreeTestQueryVo tbTreeTestQueryVo) {
        List<TreeEntity> list = tbTreeTestService.queryList(tbTreeTestQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest.list')")
    @ApiOperation(value = "所有树测试列表")
    @GetMapping("findAll")
    public Result findAllTbTreeTest() {
        //调用service的方法实现查询所有的操作
        List<TbTreeTest> list = tbTreeTestService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest.list')")
    @ApiOperation(value = "获取树测试")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbTreeTest tbTreeTest = tbTreeTestService.getById(id);
        TbTreeTest parent = tbTreeTestService.getById(tbTreeTest.getParentId());
        if (parent != null) {
            tbTreeTest.setParentName(parent.getName());
            tbTreeTest.setParentId(parent.getId());
        }
        return Result.ok(tbTreeTest);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest.list')")
    @ApiOperation(value = "获取树测试集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbTreeTest> list = tbTreeTestService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "树测试", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest.add')")
    @ApiOperation(value = "保存树测试")
    @PostMapping("/save")
    public Result save(@RequestBody TbTreeTest tbTreeTest) {
        tbTreeTestService.save(tbTreeTest);
        return Result.ok();
    }

    @Log(title = "树测试", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest.update')")
    @ApiOperation(value = "更新树测试")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbTreeTest tbTreeTest) {
        tbTreeTestService.updateById(tbTreeTest);
        return Result.ok();
    }

    @Log(title = "树测试", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest.remove')")
    @ApiOperation(value = "删除树测试")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbTreeTestService.removeById(id);
        return Result.ok();
    }

    @Log(title = "树测试", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbTreeTestService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
}
