package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbTreeTest2;
import com.lanf.business.vo.TbTreeTest2QueryVo;
import com.lanf.model.base.TreeEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试2 Service接口
 * @date 2023-11-24 00:28:41
 */
public interface TbTreeTest2Service extends IService<TbTreeTest2> {
    IPage<TbTreeTest2> selectPage(Page<TbTreeTest2> pageParam, TbTreeTest2QueryVo queryVo);

    List<TreeEntity> queryList(TbTreeTest2QueryVo queryVo);

    public boolean removeById(Serializable id);

    public boolean save(TbTreeTest2 tbTreeTest2);

    public boolean updateById(TbTreeTest2 tbTreeTest2);

    public TbTreeTest2 getById(String id);

    public List<TbTreeTest2> getByIds(List<String> ids);
}
