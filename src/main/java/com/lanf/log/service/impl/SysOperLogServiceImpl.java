package com.lanf.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.log.mapper.SysOperLogMapper;
import com.lanf.log.service.SysOperLogService;
import com.lanf.model.system.SysOperLog;
import com.lanf.model.vo.SysOperLogQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 操作日志记录 Service实现类
* @date 2023-04-30 21:39:39
*/
@Transactional
@Service
public class SysOperLogServiceImpl extends ServiceImpl
<SysOperLogMapper, SysOperLog> implements SysOperLogService {
    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Override
    public IPage<SysOperLog> selectPage(Page<SysOperLog> pageParam, SysOperLogQueryVo sysOperLogQueryVo) {
        //QueryWrapper<SysOperLog> queryWrapper = new QueryWrapper<>();
        //return sysOperLogMapper.selectPage(pageParam,queryWrapper);
        return sysOperLogMapper.selectPage(pageParam,sysOperLogQueryVo);
    }

    @Override
    public List<SysOperLog> queryList(SysOperLogQueryVo sysOperLogQueryVo){
        return sysOperLogMapper.queryList(sysOperLogQueryVo);
    }
    @Override
    public boolean save(SysOperLog sysOperLog){
        int result = this.sysOperLogMapper.insert(sysOperLog);
        return result>0;
    }
    @Override
    public boolean updateById(SysOperLog sysOperLog){
        int row = this.sysOperLogMapper.updateById(sysOperLog);
        return row>0;
    }
    @Override
    public SysOperLog getById(String id){
         SysOperLog sysOperLog = sysOperLogMapper.selectById(id);
        return sysOperLog;
    }
   @Override
   public List<SysOperLog> getByIds(List<String> ids) {
      List<SysOperLog> list = this.sysOperLogMapper.selectBatchIds(ids);
           return list;
     }
    /**
    @Override
    public void updateStatus(String id, Integer status) {
        SysOperLog sysOperLog = sysOperLogMapper.selectById(id);
        sysOperLog.setStatus(status);
        sysOperLogMapper.updateById(sysOperLog);
    }**/
}
