package com.lanf.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lanf.business.mapper.TbStudentMapper;
import com.lanf.business.model.TbStudent;
import com.lanf.business.model.TbStudentCourse;
import com.lanf.business.model.TbStudentTeacher;
import com.lanf.business.model.TbStudentTreeRel;
import com.lanf.business.service.TbStudentCourseService;
import com.lanf.business.service.TbStudentService;
import com.lanf.business.service.TbStudentTeacherService;
import com.lanf.business.service.TbStudentTreeRelService;
import com.lanf.business.vo.TbStudentExportVo;
import com.lanf.business.vo.TbStudentQueryVo;
import com.lanf.common.result.ResultCodeEnum;
import com.lanf.model.system.SysUser;
import com.lanf.system.easyexcel.ExcelHandler;
import com.lanf.system.exception.LanfException;
import com.lanf.system.utils.UserUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生 Service实现类
 * @date 2023-11-24 00:02:09
 */
@Transactional
@Service
public class TbStudentServiceImpl extends ServiceImpl
        <TbStudentMapper, TbStudent> implements TbStudentService {
    @Autowired
    private TbStudentMapper tbStudentMapper;
    @Autowired
    private TbStudentTreeRelService tbStudentTreeRelService;
    @Autowired
    private TbStudentCourseService tbStudentCourseService;
    @Autowired
    private TbStudentTeacherService tbStudentTeacherService;
    @Resource
    private ExcelHandler excelHandler;

    @Override
    public IPage<TbStudent> selectPage(Page<TbStudent> pageParam, TbStudentQueryVo tbStudentQueryVo) {
        //QueryWrapper<TbStudent> queryWrapper = new QueryWrapper<>();
        //return tbStudentMapper.selectPage(pageParam,queryWrapper);
        //只查询当前登录所属部门数据
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            tbStudentQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
                return null;
            }
            tbStudentQueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        return tbStudentMapper.selectPage(pageParam, tbStudentQueryVo);
    }

    @Override
    public List<TbStudent> queryList(TbStudentQueryVo tbStudentQueryVo) {
        //只查询当前登录所属部门数据
        SysUser sysUser = UserUtil.getUserInfo();
        if ("1".equals(sysUser.getId())) {
            tbStudentQueryVo.setCurDeptIds(null);
        } else {
            if (CollectionUtils.isEmpty(sysUser.getDeptIds())) {
                return null;
            }
            tbStudentQueryVo.setCurDeptIds(sysUser.getDeptIds());
        }
        List<TbStudent> result = tbStudentMapper.queryList(tbStudentQueryVo);
        return result;
    }

    @Override
    public boolean save(TbStudent tbStudent) {
        int result = this.tbStudentMapper.insert(tbStudent);
        List<String> treesIdList = tbStudent.getTreesIdList();
        if (treesIdList != null && treesIdList.size() > 0) {
            for (String treesId : treesIdList) {
                TbStudentTreeRel tbStudentTreeRel = new TbStudentTreeRel();
                tbStudentTreeRel.setStudentId(tbStudent.getId());
                tbStudentTreeRel.setTreesId(treesId);
                tbStudentTreeRelService.save(tbStudentTreeRel);
            }
        }
        List<String> courseIdList = tbStudent.getCourseIdList();
        if (courseIdList != null && courseIdList.size() > 0) {
            for (String courseId : courseIdList) {
                TbStudentCourse tbStudentCourse = new TbStudentCourse();
                tbStudentCourse.setStudentId(tbStudent.getId());
                tbStudentCourse.setCourseId(courseId);
                tbStudentCourseService.save(tbStudentCourse);
            }
        }
        List<String> teacherIdList = tbStudent.getTeacherIdList();
        if (teacherIdList != null && teacherIdList.size() > 0) {
            for (String teacherId : teacherIdList) {
                TbStudentTeacher tbStudentTeacher = new TbStudentTeacher();
                tbStudentTeacher.setStudentId(tbStudent.getId());
                tbStudentTeacher.setTeacherId(teacherId);
                tbStudentTeacherService.save(tbStudentTeacher);
            }
        }
        return result > 0;
    }

    @Override
    public boolean updateById(TbStudent tbStudent) {
        int row = this.tbStudentMapper.updateById(tbStudent);
        if (row <= 0) {
            throw new LanfException(ResultCodeEnum.UPDATE_ERROR);
        }
        QueryWrapper<TbStudentTreeRel> tbStudentTreeRelQueryWrapper = new QueryWrapper<TbStudentTreeRel>();
        tbStudentTreeRelQueryWrapper.eq("student_id", tbStudent.getId());
        tbStudentTreeRelService.remove(tbStudentTreeRelQueryWrapper);
        List<String> treesIdList = tbStudent.getTreesIdList();
        if (treesIdList != null && treesIdList.size() > 0) {
            for (String treesId : treesIdList) {
                TbStudentTreeRel tbStudentTreeRel = new TbStudentTreeRel();
                tbStudentTreeRel.setStudentId(tbStudent.getId());
                tbStudentTreeRel.setTreesId(treesId);
                tbStudentTreeRelService.save(tbStudentTreeRel);
            }
        }
        QueryWrapper<TbStudentCourse> tbStudentCourseQueryWrapper = new QueryWrapper<TbStudentCourse>();
        tbStudentCourseQueryWrapper.eq("student_id", tbStudent.getId());
        tbStudentCourseService.remove(tbStudentCourseQueryWrapper);
        List<String> courseIdList = tbStudent.getCourseIdList();
        if (courseIdList != null && courseIdList.size() > 0) {
            for (String courseId : courseIdList) {
                TbStudentCourse tbStudentCourse = new TbStudentCourse();
                tbStudentCourse.setStudentId(tbStudent.getId());
                tbStudentCourse.setCourseId(courseId);
                tbStudentCourseService.save(tbStudentCourse);
            }
        }
        QueryWrapper<TbStudentTeacher> tbStudentTeacherQueryWrapper = new QueryWrapper<TbStudentTeacher>();
        tbStudentTeacherQueryWrapper.eq("student_id", tbStudent.getId());
        tbStudentTeacherService.remove(tbStudentTeacherQueryWrapper);
        List<String> teacherIdList = tbStudent.getTeacherIdList();
        if (teacherIdList != null && teacherIdList.size() > 0) {
            for (String teacherId : teacherIdList) {
                TbStudentTeacher tbStudentTeacher = new TbStudentTeacher();
                tbStudentTeacher.setStudentId(tbStudent.getId());
                tbStudentTeacher.setTeacherId(teacherId);
                tbStudentTeacherService.save(tbStudentTeacher);
            }
        }
        return row > 0;
    }

    @Override
    public TbStudent getById(String id) {
        TbStudent tbStudent = tbStudentMapper.getById(id);
        Function<Object, String> f = (o -> o.toString());
        QueryWrapper<TbStudentTreeRel> tbStudentTreeRelQueryWrapper = new QueryWrapper<TbStudentTreeRel>();
        tbStudentTreeRelQueryWrapper.select("trees_id");
        tbStudentTreeRelQueryWrapper.eq("student_id", tbStudent.getId());
        List<String> tbStudentTreeRelList = tbStudentTreeRelService.listObjs(tbStudentTreeRelQueryWrapper, f);
        tbStudent.setTreesIdList(tbStudentTreeRelList);
        QueryWrapper<TbStudentCourse> tbStudentCourseQueryWrapper = new QueryWrapper<TbStudentCourse>();
        tbStudentCourseQueryWrapper.select("course_id");
        tbStudentCourseQueryWrapper.eq("student_id", tbStudent.getId());
        List<String> tbStudentCourseList = tbStudentCourseService.listObjs(tbStudentCourseQueryWrapper, f);
        tbStudent.setCourseIdList(tbStudentCourseList);
        QueryWrapper<TbStudentTeacher> tbStudentTeacherQueryWrapper = new QueryWrapper<TbStudentTeacher>();
        tbStudentTeacherQueryWrapper.select("teacher_id");
        tbStudentTeacherQueryWrapper.eq("student_id", tbStudent.getId());
        List<String> tbStudentTeacherList = tbStudentTeacherService.listObjs(tbStudentTeacherQueryWrapper, f);
        tbStudent.setTeacherIdList(tbStudentTeacherList);
        return tbStudent;
    }

    @Override
    public List<TbStudent> getByIds(List<String> ids) {
        List<TbStudent> list = this.tbStudentMapper.selectBatchIds(ids);
        return list;
    }

    @Override
    public void exportData(HttpServletResponse response) {
        List<TbStudent> list = this.selectPage(new Page<TbStudent>(1, 10000), new TbStudentQueryVo()).getRecords();
        List<TbStudentExportVo> expList = new ArrayList<>();
        list.forEach(po -> {
            TbStudentExportVo vo = new TbStudentExportVo();
            BeanUtils.copyProperties(po, vo);
            expList.add(vo);
        });
        try {
            this.excelHandler.exportExcel(response, expList, TbStudentExportVo.class, "学生数据", "学生数据");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LanfException(9005, "导出失败");
        }
    }
}
