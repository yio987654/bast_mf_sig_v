package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbCourseMapper;
import com.lanf.business.model.TbCourse;
import com.lanf.business.model.TbStudentCourse;
import com.lanf.business.service.TbCourseService;
import com.lanf.business.service.TbStudentCourseService;
import com.lanf.business.vo.TbCourseExportVo;
import com.lanf.business.vo.TbCourseQueryVo;
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
import java.util.function.Function;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 课程表 Service实现类
 * @date 2023-11-24 00:26:45
 */
@Transactional
@Service
public class TbCourseServiceImpl extends ServiceImpl
        <TbCourseMapper, TbCourse> implements TbCourseService {
    @Autowired
    private TbCourseMapper tbCourseMapper;
    @Autowired
    private TbStudentCourseService tbStudentCourseService;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbCourse> selectPage(Page<TbCourse> pageParam, TbCourseQueryVo tbCourseQueryVo) {
        //QueryWrapper<TbCourse> queryWrapper = new QueryWrapper<>();
        //return tbCourseMapper.selectPage(pageParam,queryWrapper);
        return tbCourseMapper.selectPage(pageParam, tbCourseQueryVo);
    }

    @Override
    public List<TbCourse> queryList(TbCourseQueryVo tbCourseQueryVo) {
        List<TbCourse> result = tbCourseMapper.queryList(tbCourseQueryVo);
        return result;
    }

    @Override
    public boolean save(TbCourse tbCourse) {
        int result = this.tbCourseMapper.insert(tbCourse);
        List<String> studentIdList = tbCourse.getStudentIdList();
        if (studentIdList != null && studentIdList.size() > 0) {
            for (String studentId : studentIdList) {
                TbStudentCourse tbStudentCourse = new TbStudentCourse();
                tbStudentCourse.setCourseId(tbCourse.getId());
                tbStudentCourse.setStudentId(studentId);
                tbStudentCourseService.save(tbStudentCourse);
            }
        }
        return result > 0;
    }

    @Override
    public boolean updateById(TbCourse tbCourse) {
        int row = this.tbCourseMapper.updateById(tbCourse);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        QueryWrapper<TbStudentCourse> tbStudentCourseQueryWrapper = new QueryWrapper<TbStudentCourse>();
        tbStudentCourseQueryWrapper.eq("course_id", tbCourse.getId());
        tbStudentCourseService.remove(tbStudentCourseQueryWrapper);
        List<String> studentIdList = tbCourse.getStudentIdList();
        if (studentIdList != null && studentIdList.size() > 0) {
            for (String studentId : studentIdList) {
                TbStudentCourse tbStudentCourse = new TbStudentCourse();
                tbStudentCourse.setCourseId(tbCourse.getId());
                tbStudentCourse.setStudentId(studentId);
                tbStudentCourseService.save(tbStudentCourse);
            }
        }
        return row > 0;
    }

    @Override
    public TbCourse getById(String id) {
        TbCourse tbCourse = tbCourseMapper.selectById(id);
        Function<Object, String> f = (o -> o.toString());
        QueryWrapper<TbStudentCourse> tbStudentCourseQueryWrapper = new QueryWrapper<TbStudentCourse>();
        tbStudentCourseQueryWrapper.select("student_id");
        tbStudentCourseQueryWrapper.eq("course_id", tbCourse.getId());
        List<String> tbStudentCourseList = tbStudentCourseService.listObjs(tbStudentCourseQueryWrapper, f);
        tbCourse.setStudentIdList(tbStudentCourseList);
        return tbCourse;
    }

    @Override
    public List<TbCourse> getByIds(List<String> ids) {
        List<TbCourse> list = this.tbCourseMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbCourse> list = this.selectPage(new Page<TbCourse>(1, 10000), new TbCourseQueryVo()).getRecords();
        List<TbCourseExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbCourseExportVo vo = new TbCourseExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbCourseExportVo.class, "课程表数据", "课程表数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
