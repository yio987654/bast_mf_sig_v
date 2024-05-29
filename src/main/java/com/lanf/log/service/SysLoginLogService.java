package com.lanf.log.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.model.system.SysLoginLog;
import com.lanf.model.vo.SysLoginLogQueryVo;

import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 系统访问记录 Service接口
* @date 2023-04-30 21:36:41
*/
public interface SysLoginLogService extends IService<SysLoginLog> {
    IPage<SysLoginLog> selectPage(Page<SysLoginLog> pageParam, SysLoginLogQueryVo queryVo);
    List<SysLoginLog> queryList(SysLoginLogQueryVo queryVo);
    public boolean save(SysLoginLog sysLoginLog);
    public boolean updateById(SysLoginLog sysLoginLog);
    public SysLoginLog getById(String id);
    public List<SysLoginLog> getByIds(List<String> ids);
    //void updateStatus(String id, Integer status);
}
