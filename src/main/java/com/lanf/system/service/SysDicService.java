package com.lanf.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.system.model.SysDic;
import com.lanf.system.vo.SysDicQueryVo;

/**
* @author tanlingfei
* @version 1.0
* @description 字典分类 Service接口
* @date 2020-04-13 09:55:26
*/
public interface SysDicService extends IService<SysDic> {
    IPage<SysDic> selectPage(Page<SysDic> pageParam, SysDicQueryVo queryVo);
}
