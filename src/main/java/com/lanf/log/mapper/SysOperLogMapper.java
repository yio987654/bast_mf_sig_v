package com.lanf.log.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.model.system.SysOperLog;
import com.lanf.model.vo.SysOperLogQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
* @author tanlingfei
* @version 1.0
* @description 操作日志记录 Mapper层
* @date 2023-04-30 21:39:39
*/
@Repository
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
    IPage<SysOperLog> selectPage(Page<SysOperLog> page, @Param("vo") SysOperLogQueryVo sysOperLogQueryVo);
    List<SysOperLog> queryList(@Param("vo") SysOperLogQueryVo sysOperLogQueryVo);
}