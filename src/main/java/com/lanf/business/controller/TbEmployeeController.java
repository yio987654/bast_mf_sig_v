package com.lanf.business.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.business.model.TbEmployee;
import com.lanf.business.vo.TbEmployeeQueryVo;
import com.lanf.business.vo.TbEmployeeExportVo;
import com.lanf.business.service.TbEmployeeService;
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
* @author generator
* @version 1.0
* @description 员工信息
* @date 2023-11-30 10:44:29
*/
@Api(tags = "员工信息")
@RestController
@RequestMapping("/business/tbEmployee")
public class TbEmployeeController {
    @Autowired
    private TbEmployeeService tbEmployeeService;

    @Resource
    private ExcelHandler excelHandler;

    @PreAuthorize("hasAuthority('bnt.tbEmployee.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
    @PathVariable Long page,
    @ApiParam(name = "limit", value = "每页记录数", required = true)
    @PathVariable Long limit,
    @ApiParam(name = "tbEmployeeQueryVo", value = "查询对象", required = false)
    TbEmployeeQueryVo tbEmployeeQueryVo) {
        Page<TbEmployee> pageParam = new Page<>(page, limit);
        IPage<TbEmployee> pageModel = tbEmployeeService.selectPage(pageParam, tbEmployeeQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.tbEmployee.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "tbEmployeeQueryVo", value = "查询对象", required = false)
          TbEmployeeQueryVo tbEmployeeQueryVo) {
          List<TbEmployee> list = tbEmployeeService.queryList(tbEmployeeQueryVo);
          return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbEmployee.list')")
    @ApiOperation(value = "所有员工信息列表")
    @GetMapping("findAll")
    public Result findAllTbEmployee() {
        //调用service的方法实现查询所有的操作
        List<TbEmployee> list = tbEmployeeService.list(null);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.tbEmployee.list')")
    @ApiOperation(value = "获取员工信息")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        TbEmployee tbEmployee = tbEmployeeService.getById(id);
        return Result.ok(tbEmployee);
    }

    @PreAuthorize("hasAuthority('bnt.tbEmployee.list')")
    @ApiOperation(value = "获取员工信息集合")
    @PostMapping("/getByIds")
    public Result getByIds(@RequestBody List<String> idList) {
        List<TbEmployee> list = tbEmployeeService.getByIds(idList);
        return Result.ok(list);
     }

    @Log(title = "员工信息", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.tbEmployee.add')")
    @ApiOperation(value = "保存员工信息")
    @PostMapping("/save")
    public Result save(@RequestBody TbEmployee tbEmployee) {
        tbEmployeeService.save(tbEmployee);
        return Result.ok();
    }

    @Log(title = "员工信息", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.tbEmployee.update')")
    @ApiOperation(value = "更新员工信息")
    @PutMapping("/update")
    public Result updateById(@RequestBody TbEmployee tbEmployee) {
        tbEmployeeService.updateById(tbEmployee);
        return Result.ok();
    }

    @Log(title = "员工信息", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbEmployee.remove')")
    @ApiOperation(value = "删除员工信息")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        tbEmployeeService.removeById(id);
        return Result.ok();
    }

    @Log(title = "员工信息", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.tbEmployee.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = tbEmployeeService.removeByIds(idList);
        if (b) {
          return Result.ok();
        }else{
          return Result.fail();
        }
    }
    @Log(title = "员工信息", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.tbEmployee.list')")
    @ApiOperation(value = "导出员工信息")
    @GetMapping("/export")
    public void exportData(HttpServletResponse response)  {
        this.tbEmployeeService.exportData(response);
    }
}
