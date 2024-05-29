package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbTreeTest2Mapper;
import com.lanf.business.model.TbStudentTreeRel;
import com.lanf.business.model.TbTreeTest2;
import com.lanf.business.service.TbStudentTreeRelService;
import com.lanf.business.service.TbTreeTest2Service;
import com.lanf.business.vo.TbTreeTest2QueryVo;
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
import java.util.function.Function;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 树测试2 Service实现类
 * @date 2023-11-24 00:28:41
 */
@Transactional
@Service
public class TbTreeTest2ServiceImpl extends ServiceImpl
        <TbTreeTest2Mapper, TbTreeTest2> implements TbTreeTest2Service {
    @Autowired
    private TbTreeTest2Mapper tbTreeTest2Mapper;
    @Autowired
    private TbStudentTreeRelService tbStudentTreeRelService;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbTreeTest2> selectPage(Page<TbTreeTest2> pageParam, TbTreeTest2QueryVo tbTreeTest2QueryVo) {
        //QueryWrapper<TbTreeTest2> queryWrapper = new QueryWrapper<>();
        //return tbTreeTest2Mapper.selectPage(pageParam,queryWrapper);
        return tbTreeTest2Mapper.selectPage(pageParam, tbTreeTest2QueryVo);
    }

    @Override
    public List<TreeEntity> queryList(TbTreeTest2QueryVo tbTreeTest2QueryVo) {
        List<TbTreeTest2> result = tbTreeTest2Mapper.queryList(tbTreeTest2QueryVo);
        if (CollectionUtils.isEmpty(result)) return null;
        //构建树形数据
        List<TreeEntity> treeList = TreeHelper.buildTree(result);
        return treeList;
    }

    private String getTreePath(TbTreeTest2 tbTreeTest2) {
        if (tbTreeTest2.getParentId() == null || "0".equals(tbTreeTest2.getParentId())) {
            return "," + tbTreeTest2.getId() + ",";
        } else {
            TbTreeTest2 tbTreeTest21 = this.getById(tbTreeTest2.getParentId());
            if (!StringUtils.isEmpty(tbTreeTest21.getTreePath())) {
                return tbTreeTest21.getTreePath() + tbTreeTest2.getId() + ",";
            }
            return getTreePath(tbTreeTest21) + tbTreeTest2.getId() + ",";
        }
    }

    @Override
    public boolean save(TbTreeTest2 tbTreeTest2) {
        int result = this.tbTreeTest2Mapper.insert(tbTreeTest2);
        List<String> studentIdList = tbTreeTest2.getStudentIdList();
        if (studentIdList != null && studentIdList.size() > 0) {
            for (String studentId : studentIdList) {
                TbStudentTreeRel tbStudentTreeRel = new TbStudentTreeRel();
                tbStudentTreeRel.setTreesId(tbTreeTest2.getId());
                tbStudentTreeRel.setStudentId(studentId);
                tbStudentTreeRelService.save(tbStudentTreeRel);
            }
        }
        if ("0".equals(tbTreeTest2.getParentId())) {
            tbTreeTest2.setLevel(1);
        } else {
            TbTreeTest2 tbTreeTest21 = this.getById(tbTreeTest2.getParentId());
            tbTreeTest2.setLevel(tbTreeTest21.getLevel() + 1);
        }
        String treePath = this.getTreePath(tbTreeTest2);
        tbTreeTest2.setTreePath(treePath);
        this.tbTreeTest2Mapper.updateById(tbTreeTest2);
        return result > 0;
    }

    @Override
    public boolean updateById(TbTreeTest2 tbTreeTest2) {
        if ("0".equals(tbTreeTest2.getParentId())) {
            tbTreeTest2.setLevel(1);
        } else {
            TbTreeTest2 tbTreeTest21 = this.getById(tbTreeTest2.getParentId());
            tbTreeTest2.setLevel(tbTreeTest21.getLevel() + 1);
        }
        String treePath = this.getTreePath(tbTreeTest2);
        tbTreeTest2.setTreePath(treePath);
        int row = this.tbTreeTest2Mapper.updateById(tbTreeTest2);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        QueryWrapper<TbStudentTreeRel> tbStudentTreeRelQueryWrapper = new QueryWrapper<TbStudentTreeRel>();
        tbStudentTreeRelQueryWrapper.eq("trees_id", tbTreeTest2.getId());
        tbStudentTreeRelService.remove(tbStudentTreeRelQueryWrapper);
        List<String> studentIdList = tbTreeTest2.getStudentIdList();
        if (studentIdList != null && studentIdList.size() > 0) {
            for (String studentId : studentIdList) {
                TbStudentTreeRel tbStudentTreeRel = new TbStudentTreeRel();
                tbStudentTreeRel.setTreesId(tbTreeTest2.getId());
                tbStudentTreeRel.setStudentId(studentId);
                tbStudentTreeRelService.save(tbStudentTreeRel);
            }
        }
        return row > 0;
    }

    @Override
    public TbTreeTest2 getById(String id) {
        TbTreeTest2 tbTreeTest2 = tbTreeTest2Mapper.selectById(id);
        Function<Object, String> f = (o -> o.toString());
        QueryWrapper<TbStudentTreeRel> tbStudentTreeRelQueryWrapper = new QueryWrapper<TbStudentTreeRel>();
        tbStudentTreeRelQueryWrapper.select("student_id");
        tbStudentTreeRelQueryWrapper.eq("trees_id", tbTreeTest2.getId());
        List<String> tbStudentTreeRelList = tbStudentTreeRelService.listObjs(tbStudentTreeRelQueryWrapper, f);
        tbTreeTest2.setStudentIdList(tbStudentTreeRelList);
        return tbTreeTest2;
    }

    @Override
    public List<TbTreeTest2> getByIds(List<String> ids) {
        List<TbTreeTest2> list = this.tbTreeTest2Mapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public boolean removeById(Serializable id) {
        int count = this.count(new QueryWrapper<TbTreeTest2>().eq("parent_id", id));
        if (count > 0) {
            throw new LanfException(ResultCodeEnum.NODE_ERROR);
        }
        tbTreeTest2Mapper.deleteById(id);
        return false;
    }
}
