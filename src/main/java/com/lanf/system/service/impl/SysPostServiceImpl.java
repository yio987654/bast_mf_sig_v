package com.lanf.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.system.mapper.SysPostMapper;
import com.lanf.system.model.SysPost;
import com.lanf.system.service.SysPostService;
import com.lanf.system.vo.SysPostQueryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 岗位信息表 Service实现类
 * @date 2023-04-30 12:37:35
 */
@Transactional
@Service
public class SysPostServiceImpl extends ServiceImpl
        <SysPostMapper, SysPost> implements SysPostService {
    @Autowired
    private SysPostMapper sysPostMapper;

    @Override
    public IPage<SysPost> selectPage(Page<SysPost> pageParam, SysPostQueryVo sysPostQueryVo) {
        return sysPostMapper.selectPage(pageParam, sysPostQueryVo);
    }

    @Override
    public List<SysPost> queryList(SysPostQueryVo sysPostQueryVo) {
        return sysPostMapper.queryList(sysPostQueryVo);
    }

    @Override
    public boolean save(SysPost sysPost) {
        int result = this.sysPostMapper.insert(sysPost);
        return result > 0;
    }

    @Override
    public boolean updateById(SysPost sysPost) {
        int row = this.sysPostMapper.updateById(sysPost);
        return row > 0;
    }

    @Override
    public SysPost getById(String id) {
        SysPost sysPost = sysPostMapper.selectById(id);
        return sysPost;
    }

    @Override
    public List<SysPost> getByIds(List<String> ids) {
        List<SysPost> list = this.sysPostMapper.selectBatchIds(ids);
        return list;
    }
}
