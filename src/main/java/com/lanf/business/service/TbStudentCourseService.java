package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbStudentCourse;
import com.lanf.business.vo.TbStudentCourseQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生选课程关系表 Service接口
 * @date 2023-11-23 16:56:56
 */
public interface TbStudentCourseService extends IService<TbStudentCourse> {
    IPage<TbStudentCourse> selectPage(Page<TbStudentCourse> pageParam, TbStudentCourseQueryVo queryVo);

    List<TbStudentCourse> queryList(TbStudentCourseQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbStudentCourse tbStudentCourse);

    public boolean updateById(TbStudentCourse tbStudentCourse);

    public TbStudentCourse getById(String id);

    public List<TbStudentCourse> getByIds(List<String> ids);
}
