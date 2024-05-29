package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbTreeTestMapper;
import com.lanf.business.model.TbTreeTest;
import com.lanf.business.service.TbTreeTestService;
import com.lanf.business.vo.TbTreeTestQueryVo;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.model.base.TreeEntity;
import com.lanf.system.easyexcel.ExcelHandler;
import com.lanf.system.exception.LanfException;
import com.lanf.system.utils.TreeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试 Service实现类
 * @date 2023-11-23 16:57:00
 */
@Transactional
@Service
public class TbTreeTestServiceImpl extends ServiceImpl
        <TbTreeTestMapper, TbTreeTest> implements TbTreeTestService {
    @Autowired
    private TbTreeTestMapper tbTreeTestMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbTreeTest> selectPage(Page<TbTreeTest> pageParam, TbTreeTestQueryVo tbTreeTestQueryVo) {
        //QueryWrapper<TbTreeTest> queryWrapper = new QueryWrapper<>();
        //return tbTreeTestMapper.selectPage(pageParam,queryWrapper);
        return tbTreeTestMapper.selectPage(pageParam, tbTreeTestQueryVo);
    }

    @Override
    public List<TreeEntity> queryList(TbTreeTestQueryVo tbTreeTestQueryVo) {
        List<TbTreeTest> result = tbTreeTestMapper.queryList(tbTreeTestQueryVo);
        if (CollectionUtils.isEmpty(result)) return null;
        //构建树形数据
        List<TreeEntity> treeList = TreeHelper.buildTree(result);
        return treeList;
    }

    private String getTreePath(TbTreeTest tbTreeTest) {
        if (tbTreeTest.getParentId() == null || "0".equals(tbTreeTest.getParentId())) {
            return "," + tbTreeTest.getId() + ",";
        } else {
            TbTreeTest tbTreeTest1 = this.getById(tbTreeTest.getParentId());
            if (!StringUtils.isEmpty(tbTreeTest1.getTreePath())) {
                return tbTreeTest1.getTreePath() + tbTreeTest.getId() + ",";
            }
            return getTreePath(tbTreeTest1) + tbTreeTest.getId() + ",";
        }
    }

    @Override
    public boolean save(TbTreeTest tbTreeTest) {
        int result = this.tbTreeTestMapper.insert(tbTreeTest);
        if ("0".equals(tbTreeTest.getParentId())) {
            tbTreeTest.setLevel(1);
        } else {
            TbTreeTest tbTreeTest1 = this.getById(tbTreeTest.getParentId());
            tbTreeTest.setLevel(tbTreeTest1.getLevel() + 1);
        }
        String treePath = this.getTreePath(tbTreeTest);
        tbTreeTest.setTreePath(treePath);
        this.tbTreeTestMapper.updateById(tbTreeTest);
        return result > 0;
    }

    @Override
    public boolean updateById(TbTreeTest tbTreeTest) {
        if ("0".equals(tbTreeTest.getParentId())) {
            tbTreeTest.setLevel(1);
        } else {
            TbTreeTest tbTreeTest1 = this.getById(tbTreeTest.getParentId());
            tbTreeTest.setLevel(tbTreeTest1.getLevel() + 1);
        }
        String treePath = this.getTreePath(tbTreeTest);
        tbTreeTest.setTreePath(treePath);
        int row = this.tbTreeTestMapper.updateById(tbTreeTest);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        return row > 0;
    }

    @Override
    public TbTreeTest getById(String id) {
        TbTreeTest tbTreeTest = tbTreeTestMapper.selectById(id);
        return tbTreeTest;
    }

    @Override
    public List<TbTreeTest> getByIds(List<String> ids) {
        List<TbTreeTest> list = this.tbTreeTestMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(new QueryWrapper<TbTreeTest>().eq("parent_id", id));
        if (count > 0) {
            throw new LanfException(ResultCodeEnum.NODE_ERROR);
        }
        tbTreeTestMapper.deleteById(id);
        return false;
    }
}
