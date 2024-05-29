package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbTeacherMapper;
import com.lanf.business.model.TbStudentTeacher;
import com.lanf.business.model.TbTeacher;
import com.lanf.business.service.TbStudentTeacherService;
import com.lanf.business.service.TbTeacherService;
import com.lanf.business.vo.TbTeacherExportVo;
import com.lanf.business.vo.TbTeacherQueryVo;
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
 * @description 讲师 Service实现类
 * @date 2023-11-24 00:27:34
 */
@Transactional
@Service
public class TbTeacherServiceImpl extends ServiceImpl
        <TbTeacherMapper, TbTeacher> implements TbTeacherService {
    @Autowired
    private TbTeacherMapper tbTeacherMapper;
    @Autowired
    private TbStudentTeacherService tbStudentTeacherService;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbTeacher> selectPage(Page<TbTeacher> pageParam, TbTeacherQueryVo tbTeacherQueryVo) {
        //QueryWrapper<TbTeacher> queryWrapper = new QueryWrapper<>();
        //return tbTeacherMapper.selectPage(pageParam,queryWrapper);
        return tbTeacherMapper.selectPage(pageParam, tbTeacherQueryVo);
    }

    @Override
    public List<TbTeacher> queryList(TbTeacherQueryVo tbTeacherQueryVo) {
        List<TbTeacher> result = tbTeacherMapper.queryList(tbTeacherQueryVo);
        return result;
    }

    @Override
    public boolean save(TbTeacher tbTeacher) {
        int result = this.tbTeacherMapper.insert(tbTeacher);
        List<String> studentIdList = tbTeacher.getStudentIdList();
        if (studentIdList != null && studentIdList.size() > 0) {
            for (String studentId : studentIdList) {
                TbStudentTeacher tbStudentTeacher = new TbStudentTeacher();
                tbStudentTeacher.setTeacherId(tbTeacher.getId());
                tbStudentTeacher.setStudentId(studentId);
                tbStudentTeacherService.save(tbStudentTeacher);
            }
        }
        return result > 0;
    }

    @Override
    public boolean updateById(TbTeacher tbTeacher) {
        int row = this.tbTeacherMapper.updateById(tbTeacher);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        QueryWrapper<TbStudentTeacher> tbStudentTeacherQueryWrapper = new QueryWrapper<TbStudentTeacher>();
        tbStudentTeacherQueryWrapper.eq("teacher_id", tbTeacher.getId());
        tbStudentTeacherService.remove(tbStudentTeacherQueryWrapper);
        List<String> studentIdList = tbTeacher.getStudentIdList();
        if (studentIdList != null && studentIdList.size() > 0) {
            for (String studentId : studentIdList) {
                TbStudentTeacher tbStudentTeacher = new TbStudentTeacher();
                tbStudentTeacher.setTeacherId(tbTeacher.getId());
                tbStudentTeacher.setStudentId(studentId);
                tbStudentTeacherService.save(tbStudentTeacher);
            }
        }
        return row > 0;
    }

    @Override
    public TbTeacher getById(String id) {
        TbTeacher tbTeacher = tbTeacherMapper.selectById(id);
        Function<Object, String> f = (o -> o.toString());
        QueryWrapper<TbStudentTeacher> tbStudentTeacherQueryWrapper = new QueryWrapper<TbStudentTeacher>();
        tbStudentTeacherQueryWrapper.select("student_id");
        tbStudentTeacherQueryWrapper.eq("teacher_id", tbTeacher.getId());
        List<String> tbStudentTeacherList = tbStudentTeacherService.listObjs(tbStudentTeacherQueryWrapper, f);
        tbTeacher.setStudentIdList(tbStudentTeacherList);
        return tbTeacher;
    }

    @Override
    public List<TbTeacher> getByIds(List<String> ids) {
        List<TbTeacher> list = this.tbTeacherMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbTeacher> list = this.selectPage(new Page<TbTeacher>(1, 10000), new TbTeacherQueryVo()).getRecords();
        List<TbTeacherExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbTeacherExportVo vo = new TbTeacherExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbTeacherExportVo.class, "讲师数据", "讲师数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
