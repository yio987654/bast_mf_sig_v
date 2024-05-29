package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbTreeTest2;
import com.lanf.business.service.TbTreeTest2Service;
import com.lanf.business.vo.TbTreeTest2QueryVo;
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
 * @description 树测试2
 * @date 2023-11-24 00:28:41
 */
@Api(tags = "树测试2")
@RestController
@RequestMapping("/business/tbTreeTest2")
public class TbTreeTest2Controller {
    @Autowired
    private TbTreeTest2Service tbTreeTest2Service;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbTreeTest2QueryVo", value = "查询对象", required = false)
                        TbTreeTest2QueryVo tbTreeTest2QueryVo) {
        Page<TbTreeTest2> pageParam = new Page<>(page, limit);
        IPage<TbTreeTest2> pageModel = tbTreeTest2Service.selectPage(pageParam, tbTreeTest2QueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbTreeTest2QueryVo", value = "查询对象", required = false)
                       TbTreeTest2QueryVo tbTreeTest2QueryVo) {
        List<TreeEntity> list = tbTreeTest2Service.queryList(tbTreeTest2QueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.list')")
    @ApiOperation(value = "所有树测试2列表")
    @GetMapping("findAll")
    public Result findAllTbTreeTest2() {
        //调用service的方法实现查询所有的操作
        List<TbTreeTest2> list = tbTreeTest2Service.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.list')")
    @ApiOperation(value = "获取树测试2")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbTreeTest2 tbTreeTest2 = tbTreeTest2Service.getById(id);
        TbTreeTest2 parent = tbTreeTest2Service.getById(tbTreeTest2.getParentId());
        if (parent != null) {
            tbTreeTest2.setParentName(parent.getName());
            tbTreeTest2.setParentId(parent.getId());
        }
        return Result.ok(tbTreeTest2);
    }

    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.list')")
    @ApiOperation(value = "获取树测试2集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbTreeTest2> list = tbTreeTest2Service.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "树测试2", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.add')")
    @ApiOperation(value = "保存树测试2")
    @PostMapping("/save")
    public Result save(@RequestBody TbTreeTest2 tbTreeTest2) {
        tbTreeTest2Service.save(tbTreeTest2);
        return Result.ok();
    }

    @Log(title = "树测试2", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.update')")
    @ApiOperation(value = "更新树测试2")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbTreeTest2 tbTreeTest2) {
        tbTreeTest2Service.updateById(tbTreeTest2);
        return Result.ok();
    }

    @Log(title = "树测试2", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.remove')")
    @ApiOperation(value = "删除树测试2")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbTreeTest2Service.removeById(id);
        return Result.ok();
    }

    @Log(title = "树测试2", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbTreeTest2.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbTreeTest2Service.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }
}
