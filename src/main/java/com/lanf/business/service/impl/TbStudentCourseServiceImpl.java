package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbStudentCourseMapper;
import com.lanf.business.model.TbStudentCourse;
import com.lanf.business.service.TbStudentCourseService;
import com.lanf.business.vo.TbStudentCourseExportVo;
import com.lanf.business.vo.TbStudentCourseQueryVo;
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
 * @description 学生选课程关系表 Service实现类
 * @date 2023-11-23 16:56:56
 */
@Transactional
@Service
public class TbStudentCourseServiceImpl extends ServiceImpl
        <TbStudentCourseMapper, TbStudentCourse> implements TbStudentCourseService {
    @Autowired
    private TbStudentCourseMapper tbStudentCourseMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbStudentCourse> selectPage(Page<TbStudentCourse> pageParam, TbStudentCourseQueryVo tbStudentCourseQueryVo) {
        //QueryWrapper<TbStudentCourse> queryWrapper = new QueryWrapper<>();
        //return tbStudentCourseMapper.selectPage(pageParam,queryWrapper);
        return tbStudentCourseMapper.selectPage(pageParam, tbStudentCourseQueryVo);
    }

    @Override
    public List<TbStudentCourse> queryList(TbStudentCourseQueryVo tbStudentCourseQueryVo) {
        List<TbStudentCourse> result = tbStudentCourseMapper.queryList(tbStudentCourseQueryVo);
        return result;
    }

    @Override
    public boolean save(TbStudentCourse tbStudentCourse) {
        int result = this.tbStudentCourseMapper.insert(tbStudentCourse);
        return result > 0;
    }

    @Override
    public boolean updateById(TbStudentCourse tbStudentCourse) {
        int row = this.tbStudentCourseMapper.updateById(tbStudentCourse);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        return row > 0;
    }

    @Override
    public TbStudentCourse getById(String id) {
        TbStudentCourse tbStudentCourse = tbStudentCourseMapper.selectById(id);
        return tbStudentCourse;
    }

    @Override
    public List<TbStudentCourse> getByIds(List<String> ids) {
        List<TbStudentCourse> list = this.tbStudentCourseMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbStudentCourse> list = this.selectPage(new Page<TbStudentCourse>(1, 10000), new TbStudentCourseQueryVo()).getRecords();
        List<TbStudentCourseExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbStudentCourseExportVo vo = new TbStudentCourseExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbStudentCourseExportVo.class, "学生选课程关系表数据", "学生选课程关系表数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
