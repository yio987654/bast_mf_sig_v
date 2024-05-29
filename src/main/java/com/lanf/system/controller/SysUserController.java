package com.lanf.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.common.utils.MD5;
import com.lanf.log.annotation.Log;
import com.lanf.log.type.BusinessType;
import com.lanf.model.system.SysUser;
import com.lanf.model.vo.SysUserQueryVo;
import com.lanf.system.model.SysDicItem;
import com.lanf.system.service.SysUserService;
import com.lanf.system.vo.SysDicItemQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;


    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "userQueryVo", value = "查询对象", required = false)
            SysUserQueryVo userQueryVo, HttpServletRequest request) {
        Page<SysUser> pageParam = new Page<>(page, limit);
        IPage<SysUser> pageModel = sysUserService.selectPage(pageParam, userQueryVo);
        Map<String, Object> data = new HashMap<>();
        data.put("data", pageModel);
        data.put("ctxPath", "http://" + request.getServerName() + ":" + request.getServerPort());
        return Result.ok(data);
    }

    @PreAuthorize("hasAuthority('bnt.sysDicItem.list')")
    @ApiOperation(value = "查询列表")
    @GetMapping("/list")
    public Result list(@ApiParam(name = "sysUserQueryVo", value = "查询对象", required = false)
                       SysUserQueryVo sysUserQueryVo) {
        List<SysUser> list = sysUserService.queryList(sysUserQueryVo);
        return Result.ok(list);
    }

    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "获取用户")
    @GetMapping("/getUser/{id}")
    public Result get(@PathVariable String id, HttpServletRequest request) {
        SysUser user = sysUserService.getById(id);
        user.setHeadUrl("http://" + request.getServerName() + ":" + request.getServerPort() + user.getHeadUrl());
        return Result.ok(user);
    }

    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation(value = "保存用户")
    @PostMapping("/save")
    public Result save(SysUser user) {
        String pwd = MD5.encrypt(user.getPassword());
        user.setPassword(pwd);
        user.setStatus(1);
        sysUserService.save(user);
        return Result.ok();
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("hasAuthority('bnt.sysUser.add')")
    @ApiOperation(value = "导入用户")
    @PostMapping("/import")
    public Result importUser(@RequestParam("files") MultipartFile files) {
        this.sysUserService.importData(files);
        return Result.ok();
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("hasAuthority('bnt.sysUser.list')")
    @ApiOperation(value = "导出用户")
    @GetMapping("/export")
    public void exportUser(HttpServletResponse response) {
        this.sysUserService.exportData(response);
    }

    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "更新用户")
    @PostMapping("/update")
    public Result updateById(SysUser user) {
        if (!StringUtils.isEmpty(user.getNewpassword()) && !"null".equals(user.getNewpassword())) {
            String pwd = MD5.encrypt(user.getNewpassword());
            user.setPassword(pwd);
        }
        sysUserService.updateById(user);
        return Result.ok();
    }

    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/remove/{id}")
    public Result remove(@PathVariable String id) {
        sysUserService.removeById(id);
        return Result.ok();
    }

    @Log(title = "用户管理", businessType = BusinessType.STATUS)
    @PreAuthorize("hasAuthority('bnt.sysUser.update')")
    @ApiOperation(value = "更新状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id, @PathVariable Integer status) {
        sysUserService.updateStatus(id, status);
        return Result.ok();
    }

    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @PreAuthorize("hasAuthority('bnt.sysUser.remove')")
    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("/batchRemove")
    public Result batchRemove(@RequestBody List<String> idList) {
        boolean b = sysUserService.removeByIds(idList);
        if (b) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

}
