package com.lanf.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.system.mapper.SysDicItemMapper;
import com.lanf.system.model.SysDicItem;
import com.lanf.system.service.SysDicItemService;
import com.lanf.system.vo.SysDicItemQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 字典选项 Service实现类
 * @date 2020-04-13 16:12:32
 */
@Transactional
@Service
public class SysDicItemServiceImpl extends ServiceImpl
        <SysDicItemMapper, SysDicItem> implements SysDicItemService {
    @Autowired
    private SysDicItemMapper sysDicItemMapper;

    @Override
    public IPage<SysDicItem> selectPage(Page<SysDicItem> pageParam, SysDicItemQueryVo sysDicItemQueryVo) {
        return sysDicItemMapper.selectPage(pageParam, sysDicItemQueryVo);
    }

    @Override
    public List<SysDicItem> queryList(SysDicItemQueryVo queryVo) {
        return sysDicItemMapper.queryList(queryVo);
    }

}
