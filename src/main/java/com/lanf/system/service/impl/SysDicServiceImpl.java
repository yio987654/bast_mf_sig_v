package com.lanf.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.system.mapper.SysDicMapper;
import com.lanf.system.model.SysDic;
import com.lanf.system.service.SysDicService;
import com.lanf.system.vo.SysDicQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 字典分类 Service实现类
 * @date 2020-04-13 09:55:26
 */
@Transactional
@Service
public class SysDicServiceImpl extends ServiceImpl
        <SysDicMapper, SysDic> implements SysDicService {
    @Autowired
    private SysDicMapper sysDicMapper;

    @Override
    public IPage<SysDic> selectPage(Page<SysDic> pageParam, SysDicQueryVo sysDicQueryVo) {
        return sysDicMapper.selectPage(pageParam, sysDicQueryVo);
    }
}
