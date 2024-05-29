package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.business.model.Test02;
import com.lanf.business.vo.Test02QueryVo;
import com.lanf.business.vo.Test02ExportVo;
import com.lanf.business.service.Test02Service;
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
* @description 测试表2
* @date 2024-05-09 23:57:31
*/
@Api(tags = "测试表2")
@RestController
@RequestMapping("/business/test02")
public class Test02Controller {
    @Autowired
    private Test02Service test02Service;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.test02.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
    @PathVariable Long page,
    @ApiParam(name = "limit", value = "每页记录数", required = true)
    @PathVariable Long limit,
    @ApiParam(name = "test02QueryVo", value = "查询对象", required = false)
    Test02QueryVo test02QueryVo) {
        Page<Test02> pageParam = new Page<>(page, limit);
        IPage<Test02> pageModel = test02Service.selectPage(pageParam, test02QueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.test02.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "test02QueryVo", value = "查询对象", required = false)
          Test02QueryVo test02QueryVo) {
          List<Test02> list = test02Service.queryList(test02QueryVo);
          return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.test02.list')")
    @ApiOperation(value = "所有测试表2列表")
    @GetMapping("findAll")
    public Result findAllTest02() {
        //调用service的方法实现查询所有的操作
        List<Test02> list = test02Service.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.test02.list')")
    @ApiOperation(value = "获取测试表2")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        Test02 test02 = test02Service.getById(id);
        return Result.ok(test02);
    }

    @PreAuthorize("hasAuthority('bnt.test02.list')")
    @ApiOperation(value = "获取测试表2集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<Test02> list = test02Service.getByIds(idList);
        return Result.ok(list);
     }

    @Log(title = "测试表2", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.test02.add')")
    @ApiOperation(value = "保存测试表2")
    @PostMapping("/save")
    public Result save(@RequestBody Test02 test02) {
        test02Service.save(test02);
        return Result.ok();
    }

    @Log(title = "测试表2", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.test02.update')")
    @ApiOperation(value = "更新测试表2")
    @PutMapping("/update")
    public Result updateById(@RequestBody Test02 test02) {
        test02Service.updateById(test02);
        return Result.ok();
    }

    @Log(title = "测试表2", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.test02.remove')")
    @ApiOperation(value = "删除测试表2")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        test02Service.removeById(id);
        return Result.ok();
    }

    @Log(title = "测试表2", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.test02.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = test02Service.removeByIds(idList);
        if (b) {
          return Result.ok();
        }else{
          return Result.fail();
        }
    }
    @Log(title = "测试表2", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.test02.list')")
    @ApiOperation(value = "导出测试表2")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response)  {
        this.test02Service.exportData(response);
    }
}
