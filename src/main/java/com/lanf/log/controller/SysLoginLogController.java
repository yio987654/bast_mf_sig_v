package com.lanf.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.log.service.SysLoginLogService;
import com.lanf.model.system.SysLoginLog;
import com.lanf.model.vo.SysLoginLogQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 系统访问记录
* @date 2023-04-30 21:36:41
*/
@Api(tags = "系统访问记录")
@RestController
@RequestMapping("/log/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private SysLoginLogService sysLoginLogService;

    @PreAuthorize("hasAuthority('bnt.sysLoginLog.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
    @PathVariable Long page,
    @ApiParam(name = "limit", value = "每页记录数", required = true)
    @PathVariable Long limit,
    @ApiParam(name = "sysLoginLogQueryVo", value = "查询对象", required = false)
                                SysLoginLogQueryVo sysLoginLogQueryVo) {
        Page<SysLoginLog> pageParam = new Page<>(page, limit);
        IPage<SysLoginLog> pageModel = sysLoginLogService.selectPage(pageParam, sysLoginLogQueryVo);
        return Result.ok(pageModel);
    }

    @PreAuthorize("hasAuthority('bnt.sysLoginLog.add')")
    @ApiOperation(value = "保存系统访问记录")
    @PostMapping("/save")
    public Result save(@RequestBody SysLoginLog sysLoginLog) {
        sysLoginLogService.save(sysLoginLog);
        return Result.ok();
    }
}
