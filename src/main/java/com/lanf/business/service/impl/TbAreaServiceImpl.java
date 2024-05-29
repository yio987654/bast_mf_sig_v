package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbAreaMapper;
import com.lanf.business.model.TbArea;
import com.lanf.business.service.TbAreaService;
import com.lanf.business.vo.TbAreaExportVo;
import com.lanf.business.vo.TbAreaQueryVo;
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
 * @description 地区 Service实现类
 * @date 2023-11-23 16:56:37
 */
@Transactional
@Service
public class TbAreaServiceImpl extends ServiceImpl
        <TbAreaMapper, TbArea> implements TbAreaService {
    @Autowired
    private TbAreaMapper tbAreaMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbArea> selectPage(Page<TbArea> pageParam, TbAreaQueryVo tbAreaQueryVo) {
        return tbAreaMapper.selectPage(pageParam, tbAreaQueryVo);
    }

    @Override
    public List<TbArea> queryList(TbAreaQueryVo tbAreaQueryVo) {
        List<TbArea> result = tbAreaMapper.queryList(tbAreaQueryVo);
        return result;
    }

    @Override
    public boolean save(TbArea tbArea) {
        int result = this.tbAreaMapper.insert(tbArea);
        return result > 0;
    }

    @Override
    public boolean updateById(TbArea tbArea) {
        int row = this.tbAreaMapper.updateById(tbArea);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        return row > 0;
    }

    @Override
    public TbArea getById(String id) {
        TbArea tbArea = tbAreaMapper.selectById(id);
        return tbArea;
    }

    @Override
    public List<TbArea> getByIds(List<String> ids) {
        List<TbArea> list = this.tbAreaMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbArea> list = this.selectPage(new Page<TbArea>(1, 10000), new TbAreaQueryVo()).getRecords();
        List<TbAreaExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbAreaExportVo vo = new TbAreaExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbAreaExportVo.class, "地区数据", "地区数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
