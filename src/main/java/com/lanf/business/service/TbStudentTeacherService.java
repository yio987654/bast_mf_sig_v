package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbStudentTeacher;
import com.lanf.business.vo.TbStudentTeacherQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生讲师关系表 Service接口
 * @date 2023-11-23 16:56:48
 */
public interface TbStudentTeacherService extends IService<TbStudentTeacher> {
    IPage<TbStudentTeacher> selectPage(Page<TbStudentTeacher> pageParam, TbStudentTeacherQueryVo queryVo);

    List<TbStudentTeacher> queryList(TbStudentTeacherQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbStudentTeacher tbStudentTeacher);

    public boolean updateById(TbStudentTeacher tbStudentTeacher);

    public TbStudentTeacher getById(String id);

    public List<TbStudentTeacher> getByIds(List<String> ids);
}
