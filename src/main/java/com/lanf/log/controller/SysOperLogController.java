package com.lanf.log.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.common.result.Result;
import com.lanf.log.service.SysOperLogService;
import com.lanf.model.system.SysOperLog;
import com.lanf.model.vo.SysOperLogQueryVo;
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
* @description 操作日志记录
* @date 2023-04-30 21:39:39
*/
@Api(tags = "操作日志记录")
@RestController
@RequestMapping("/log/sysOperLog")
public class SysOperLogController {
    @Autowired
    private SysOperLogService sysOperLogService;

    @PreAuthorize("hasAuthority('bnt.sysOperLog.list')")
    @ApiOperation(value = "获取分页列表")
    @GetMapping("/{page}/{limit}")
    public Result index(@ApiParam(name = "page", value = "当前页码", required = true)
    @PathVariable Long page,
    @ApiParam(name = "limit", value = "每页记录数", required = true)
    @PathVariable Long limit,
    @ApiParam(name = "sysOperLogQueryVo", value = "查询对象", required = false)
                                SysOperLogQueryVo sysOperLogQueryVo) {
        Page<SysOperLog> pageParam = new Page<>(page, limit);
        IPage<SysOperLog> pageModel = sysOperLogService.selectPage(pageParam, sysOperLogQueryVo);
        return Result.ok(pageModel);
    }


    @PreAuthorize("hasAuthority('bnt.sysOperLog.add')")
    @ApiOperation(value = "保存操作日志记录")
    @PostMapping("/save")
    public Result save(@RequestBody SysOperLog sysOperLog) {
        sysOperLogService.save(sysOperLog);
        return Result.ok();
    }



}
