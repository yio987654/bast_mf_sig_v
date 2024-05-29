package com.lanf.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.model.system.SysOperLog;
import com.lanf.model.vo.SysOperLogQueryVo;

import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 操作日志记录 Service接口
* @date 2023-04-30 21:39:39
*/
public interface SysOperLogService extends IService<SysOperLog> {
    IPage<SysOperLog> selectPage(Page<SysOperLog> pageParam, SysOperLogQueryVo queryVo);
    List<SysOperLog> queryList(SysOperLogQueryVo queryVo);
    public boolean save(SysOperLog sysOperLog);
    public boolean updateById(SysOperLog sysOperLog);
    public SysOperLog getById(String id);
    public List<SysOperLog> getByIds(List<String> ids);
    //void updateStatus(String id, Integer status);
}
