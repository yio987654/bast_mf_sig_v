package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbStudentTreeRelMapper;
import com.lanf.business.model.TbStudentTreeRel;
import com.lanf.business.service.TbStudentTreeRelService;
import com.lanf.business.vo.TbStudentTreeRelExportVo;
import com.lanf.business.vo.TbStudentTreeRelQueryVo;
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
 * @description 学生树形关系表 Service实现类
 * @date 2023-11-23 16:57:09
 */
@Transactional
@Service
public class TbStudentTreeRelServiceImpl extends ServiceImpl
        <TbStudentTreeRelMapper, TbStudentTreeRel> implements TbStudentTreeRelService {
    @Autowired
    private TbStudentTreeRelMapper tbStudentTreeRelMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbStudentTreeRel> selectPage(Page<TbStudentTreeRel> pageParam, TbStudentTreeRelQueryVo tbStudentTreeRelQueryVo) {
        //QueryWrapper<TbStudentTreeRel> queryWrapper = new QueryWrapper<>();
        //return tbStudentTreeRelMapper.selectPage(pageParam,queryWrapper);
        return tbStudentTreeRelMapper.selectPage(pageParam, tbStudentTreeRelQueryVo);
    }

    @Override
    public List<TbStudentTreeRel> queryList(TbStudentTreeRelQueryVo tbStudentTreeRelQueryVo) {
        List<TbStudentTreeRel> result = tbStudentTreeRelMapper.queryList(tbStudentTreeRelQueryVo);
        return result;
    }

    @Override
    public boolean save(TbStudentTreeRel tbStudentTreeRel) {
        int result = this.tbStudentTreeRelMapper.insert(tbStudentTreeRel);
        return result > 0;
    }

    @Override
    public boolean updateById(TbStudentTreeRel tbStudentTreeRel) {
        int row = this.tbStudentTreeRelMapper.updateById(tbStudentTreeRel);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        return row > 0;
    }

    @Override
    public TbStudentTreeRel getById(String id) {
        TbStudentTreeRel tbStudentTreeRel = tbStudentTreeRelMapper.selectById(id);
        return tbStudentTreeRel;
    }

    @Override
    public List<TbStudentTreeRel> getByIds(List<String> ids) {
        List<TbStudentTreeRel> list = this.tbStudentTreeRelMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbStudentTreeRel> list = this.selectPage(new Page<TbStudentTreeRel>(1, 10000), new TbStudentTreeRelQueryVo()).getRecords();
        List<TbStudentTreeRelExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbStudentTreeRelExportVo vo = new TbStudentTreeRelExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbStudentTreeRelExportVo.class, "学生树形关系表数据", "学生树形关系表数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
