package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbClassMapper;
import com.lanf.business.model.TbClass;
import com.lanf.business.service.TbClassService;
import com.lanf.business.vo.TbClassExportVo;
import com.lanf.business.vo.TbClassQueryVo;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.system.easyexcel.ExcelHandler;
import com.lanf.system.exception.LanfException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 班级 Service实现类
 * @date 2023-11-23 16:56:22
 */
@Transactional
@Service
public class TbClassServiceImpl extends ServiceImpl
        <TbClassMapper, TbClass> implements TbClassService {
    @Autowired
    private TbClassMapper tbClassMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbClass> selectPage(Page<TbClass> pageParam, TbClassQueryVo tbClassQueryVo) {
        //QueryWrapper<TbClass> queryWrapper = new QueryWrapper<>();
        //return tbClassMapper.selectPage(pageParam,queryWrapper);
        return tbClassMapper.selectPage(pageParam, tbClassQueryVo);
    }

    @Override
    public List<TbClass> queryList(TbClassQueryVo tbClassQueryVo) {
        List<TbClass> result = tbClassMapper.queryList(tbClassQueryVo);
        return result;
    }

    @Override
    public boolean save(TbClass tbClass) {
        int result = this.tbClassMapper.insert(tbClass);
        return result > 0;
    }

    @Override
    public boolean updateById(TbClass tbClass) {
        int row = this.tbClassMapper.updateById(tbClass);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        return row > 0;
    }

    @Override
    public TbClass getById(String id) {
        TbClass tbClass = tbClassMapper.selectById(id);
        return tbClass;
    }

    @Override
    public List<TbClass> getByIds(List<String> ids) {
        List<TbClass> list = this.tbClassMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbClass> list = this.selectPage(new Page<TbClass>(1, 10000), new TbClassQueryVo()).getRecords();
        List<TbClassExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbClassExportVo vo = new TbClassExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbClassExportVo.class, "班级数据", "班级数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
