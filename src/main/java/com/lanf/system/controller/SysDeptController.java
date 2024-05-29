package com.lanf.system.controller;

import com.lanf.common.result.Result;
import com.lanf.log.annotation.Log;
import com.lanf.log.type.BusinessType;
import com.lanf.model.system.SysDept;
import com.lanf.system.service.SysDeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "部门管理")
@RestController
@RequestMapping("/admin/system/sysDept")
public class SysDeptController {
    @Autowired
    private SysDeptService sysDeptService;

    @PreAuthorize("hasAuthority('bnt.sysDept.list')")
    @ApiOperation(value = "获取部门")
    @GetMapping("findNodes")
    public Result findNodes() {
        List<SysDept> list = sysDeptService.findNodes();
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysDept.list')")
    @ApiOperation(value = "获取部门")
    @GetMapping("findSelectNodes")
    public Result findSelectNodes() {
        List<Map> list = sysDeptService.findSelectNodes();
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysDept.list')")
    @ApiOperation(value = "获取部门")
    @GetMapping("findNodesByParent/{parentId}")
    public Result findNodesByParent(@PathVariable String parentId) {
        List<SysDept> list = sysDeptService.findNodesByParent(parentId);
        return Result.ok(list);
    }


    @PreAuthorize("hasAuthority('bnt.sysDept.list')")
    @ApiOperation(value = "获取部门详情")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        SysDept sysDept = sysDeptService.getById(id);
        SysDept parent = sysDeptService.getById(sysDept.getParentId());
        if (parent != null) {
            sysDept.setParentName(parent.getName());
            sysDept.setParentId(parent.getId());
        }
        return Result.ok(sysDept);
    }


    @Log(title = "部门管理", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysDept.add')")
    @ApiOperation(value = "新增部门")
    @PostMapping("save")
    public Result save(@RequestBody SysDept sysDept) {
        sysDeptService.save(sysDept);
        return Result.ok();
    }

    @Log(title = "部门管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysDept.update')")
    @ApiOperation(value = "修改部门")
    @PutMapping("update")
    public Result updateById(@RequestBody SysDept sysDept) {
        sysDeptService.updateById(sysDept);
        return Result.ok();
    }

    @Log(title = "部门管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysDept.remove')")
    @ApiOperation(value = "删除部门")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        sysDeptService.removeById(id);
        return Result.ok();
    }
}
