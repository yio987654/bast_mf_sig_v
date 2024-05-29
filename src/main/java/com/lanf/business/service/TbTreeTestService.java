package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbTreeTest;
import com.lanf.business.vo.TbTreeTestQueryVo;
import com.lanf.model.base.TreeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试 Service接口
 * @date 2023-11-23 16:57:00
 */
public interface TbTreeTestService extends IService<TbTreeTest> {
    IPage<TbTreeTest> selectPage(Page<TbTreeTest> pageParam, TbTreeTestQueryVo queryVo);

    List<TreeEntity> queryList(TbTreeTestQueryVo queryVo);

    public boolean removeById(Serializable id);

    public boolean save(TbTreeTest tbTreeTest);

    public boolean updateById(TbTreeTest tbTreeTest);

    public TbTreeTest getById(String id);

    public List<TbTreeTest> getByIds(List<String> ids);
}
