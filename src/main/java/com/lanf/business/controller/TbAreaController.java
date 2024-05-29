package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbArea;
import com.lanf.business.service.TbAreaService;
import com.lanf.business.vo.TbAreaQueryVo;
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
 * @description 地区
 * @date 2023-11-23 16:56:37
 */
@Api(tags = "地区")
@RestController
@RequestMapping("/business/tbArea")
public class TbAreaController {
    @Autowired
    private TbAreaService tbAreaService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbArea.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
                        @PathVariable Long page,
                        @ApiParam(name = "limit", value = "每页记录数", required = true)
                        @PathVariable Long limit,
                        @ApiParam(name = "tbAreaQueryVo", value = "查询对象", required = false)
                        TbAreaQueryVo tbAreaQueryVo) {
        Page<TbArea> pageParam = new Page<>(page, limit);
        IPage<TbArea> pageModel = tbAreaService.selectPage(pageParam, tbAreaQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbArea.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbAreaQueryVo", value = "查询对象", required = false)
                       TbAreaQueryVo tbAreaQueryVo) {
        List<TbArea> list = tbAreaService.queryList(tbAreaQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbArea.list')")
    @ApiOperation(value = "所有地区列表")
    @GetMapping("findAll")
    public Result findAllTbArea() {
        //调用service的方法实现查询所有的操作
        List<TbArea> list = tbAreaService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbArea.list')")
    @ApiOperation(value = "获取地区")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbArea tbArea = tbAreaService.getById(id);
        return Result.ok(tbArea);
    }

    @PreAuthorize("hasAuthority('bnt.tbArea.list')")
    @ApiOperation(value = "获取地区集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbArea> list = tbAreaService.getByIds(idList);
        return Result.ok(list);
    }

    @Log(title = "地区", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbArea.add')")
    @ApiOperation(value = "保存地区")
    @PostMapping("/save")
    public Result save(@RequestBody TbArea tbArea) {
        tbAreaService.save(tbArea);
        return Result.ok();
    }

    @Log(title = "地区", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbArea.update')")
    @ApiOperation(value = "更新地区")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbArea tbArea) {
        tbAreaService.updateById(tbArea);
        return Result.ok();
    }

    @Log(title = "地区", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbArea.remove')")
    @ApiOperation(value = "删除地区")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbAreaService.removeById(id);
        return Result.ok();
    }

    @Log(title = "地区", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbArea.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbAreaService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @Log(title = "地区", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbArea.list')")
    @ApiOperation(value = "导出地区")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response) {
        this.tbAreaService.exportData(response);
    }
}
