package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.business.model.YioDebug;
import com.lanf.business.vo.YioDebugQueryVo;
import com.lanf.business.vo.YioDebugExportVo;
import com.lanf.business.service.YioDebugService;
import com.lanf.log.type.BusinessType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.lanf.log.annotation.Log;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import org.springframework.beans.BeanUtils;
import org.springframework.web.multipart.MultipartFile;
import com.lanf.system.easyexcel.ExcelHandler;
import com.lanf.system.exception.LanfException;
import javax.annotation.Resource;
/**
* @author tanlingfei
* @version 1.0
* @description yio测试
* @date 2024-05-09 23:38:22
*/
@Api(tags = "yio测试")
@RestController
@RequestMapping("/business/yioDebug")
public class YioDebugController {
    @Autowired
    private YioDebugService yioDebugService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.yioDebug.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
    @PathVariable Long page,
    @ApiParam(name = "limit", value = "每页记录数", required = true)
    @PathVariable Long limit,
    @ApiParam(name = "yioDebugQueryVo", value = "查询对象", required = false)
    YioDebugQueryVo yioDebugQueryVo) {
        Page<YioDebug> pageParam = new Page<>(page, limit);
        IPage<YioDebug> pageModel = yioDebugService.selectPage(pageParam, yioDebugQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.yioDebug.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "yioDebugQueryVo", value = "查询对象", required = false)
          YioDebugQueryVo yioDebugQueryVo) {
          List<YioDebug> list = yioDebugService.queryList(yioDebugQueryVo);
          return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.yioDebug.list')")
    @ApiOperation(value = "所有yio测试列表")
    @GetMapping("findAll")
    public Result findAllYioDebug() {
        //调用service的方法实现查询所有的操作
        List<YioDebug> list = yioDebugService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.yioDebug.list')")
    @ApiOperation(value = "获取yio测试")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        YioDebug yioDebug = yioDebugService.getById(id);
        return Result.ok(yioDebug);
    }

    @PreAuthorize("hasAuthority('bnt.yioDebug.list')")
    @ApiOperation(value = "获取yio测试集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<YioDebug> list = yioDebugService.getByIds(idList);
        return Result.ok(list);
     }

    @Log(title = "yio测试", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.yioDebug.add')")
    @ApiOperation(value = "保存yio测试")
    @PostMapping("/save")
    public Result save(@RequestBody YioDebug yioDebug) {
        yioDebugService.save(yioDebug);
        return Result.ok();
    }

    @Log(title = "yio测试", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.yioDebug.update')")
    @ApiOperation(value = "更新yio测试")
    @PutMapping("/update")
    public Result updateById(@RequestBody YioDebug yioDebug) {
        yioDebugService.updateById(yioDebug);
        return Result.ok();
    }

    @Log(title = "yio测试", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.yioDebug.remove')")
    @ApiOperation(value = "删除yio测试")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        yioDebugService.removeById(id);
        return Result.ok();
    }

    @Log(title = "yio测试", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.yioDebug.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = yioDebugService.removeByIds(idList);
        if (b) {
          return Result.ok();
        }else{
          return Result.fail();
        }
    }
    @Log(title = "yio测试", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.yioDebug.list')")
    @ApiOperation(value = "导出yio测试")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response)  {
        this.yioDebugService.exportData(response);
    }
}
