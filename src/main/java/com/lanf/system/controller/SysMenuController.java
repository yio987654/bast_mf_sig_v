package com.lanf.system.controller;

import com.lanf.common.result.Result;
import com.lanf.log.annotation.Log;
import com.lanf.log.type.BusinessType;
import com.lanf.model.system.SysDept;
import com.lanf.model.system.SysMenu;
import com.lanf.model.vo.AssginMenuVo;
import com.lanf.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation(value = "获取菜单")
    @GetMapping("findNodes")
    public Result findNodes() {
        List<SysMenu> list = sysMenuService.findNodes();
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation(value = "获取菜单")
    @GetMapping("findDir")
    public Result findDir() {
        List<SysMenu> list = sysMenuService.findDir();
        return Result.ok(list);
    }

    @Log(title = "菜单管理", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysMenu.add')")
    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu permission) {
        sysMenuService.save(permission);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation(value = "获取菜单详情")
    @GetMapping("/get/{id}")
    public Result get(@PathVariable String id) {
        SysMenu sysMenu = sysMenuService.getById(id);
        SysMenu parent = sysMenuService.getById(sysMenu.getParentId());
        if (parent != null) {
            sysMenu.setParentName(parent.getName());
        }
        return Result.ok(sysMenu);
    }

    @Log(title = "菜单管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysMenu.update')")
    @ApiOperation(value = "修改菜单")
    @PostMapping("update")
    public Result updateById(@RequestBody SysMenu permission) {
        sysMenuService.updateById(permission);
        return Result.ok();
    }

    @Log(title = "菜单管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysMenu.remove')")
    @ApiOperation(value = "删除菜单")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable String id) {
        sysMenuService.removeById(id);
        return Result.ok();
    }

    @PreAuthorize("hasAuthority('bnt.sysMenu.list')")
    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("/toAssign/{roleId}")
    public Result toAssign(@PathVariable String roleId) {
        List<SysMenu> list = sysMenuService.findSysMenuByRoleId(roleId);
        return Result.ok(list);
    }

    @Log(title = "菜单管理", businessType = BusinessType.ASSGIN)
    @PreAuthorize("hasAuthority('bnt.sysRole.assignAuth')")
    @ApiOperation(value = "给角色分配权限")
    @PostMapping("/doAssign")
    public Result doAssign(@RequestBody AssginMenuVo assginMenuVo) {
        sysMenuService.doAssign(assginMenuVo);
        return Result.ok();
    }

}
