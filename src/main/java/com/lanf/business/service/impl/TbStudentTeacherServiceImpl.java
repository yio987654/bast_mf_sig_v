package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbStudentTeacherMapper;
import com.lanf.business.model.TbStudentTeacher;
import com.lanf.business.service.TbStudentTeacherService;
import com.lanf.business.vo.TbStudentTeacherExportVo;
import com.lanf.business.vo.TbStudentTeacherQueryVo;
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
 * @description 学生讲师关系表 Service实现类
 * @date 2023-11-23 16:56:48
 */
@Transactional
@Service
public class TbStudentTeacherServiceImpl extends ServiceImpl
        <TbStudentTeacherMapper, TbStudentTeacher> implements TbStudentTeacherService {
    @Autowired
    private TbStudentTeacherMapper tbStudentTeacherMapper;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbStudentTeacher> selectPage(Page<TbStudentTeacher> pageParam, TbStudentTeacherQueryVo tbStudentTeacherQueryVo) {
        //QueryWrapper<TbStudentTeacher> queryWrapper = new QueryWrapper<>();
        //return tbStudentTeacherMapper.selectPage(pageParam,queryWrapper);
        return tbStudentTeacherMapper.selectPage(pageParam, tbStudentTeacherQueryVo);
    }

    @Override
    public List<TbStudentTeacher> queryList(TbStudentTeacherQueryVo tbStudentTeacherQueryVo) {
        List<TbStudentTeacher> result = tbStudentTeacherMapper.queryList(tbStudentTeacherQueryVo);
        return result;
    }

    @Override
    public boolean save(TbStudentTeacher tbStudentTeacher) {
        int result = this.tbStudentTeacherMapper.insert(tbStudentTeacher);
        return result > 0;
    }

    @Override
    public boolean updateById(TbStudentTeacher tbStudentTeacher) {
        int row = this.tbStudentTeacherMapper.updateById(tbStudentTeacher);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        return row > 0;
    }

    @Override
    public TbStudentTeacher getById(String id) {
        TbStudentTeacher tbStudentTeacher = tbStudentTeacherMapper.selectById(id);
        return tbStudentTeacher;
    }

    @Override
    public List<TbStudentTeacher> getByIds(List<String> ids) {
        List<TbStudentTeacher> list = this.tbStudentTeacherMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbStudentTeacher> list = this.selectPage(new Page<TbStudentTeacher>(1, 10000), new TbStudentTeacherQueryVo()).getRecords();
        List<TbStudentTeacherExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbStudentTeacherExportVo vo = new TbStudentTeacherExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbStudentTeacherExportVo.class, "学生讲师关系表数据", "学生讲师关系表数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
