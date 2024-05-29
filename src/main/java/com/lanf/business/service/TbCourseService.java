package com.lanf.business.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lanf.business.model.TbCourse;
import com.lanf.business.vo.TbCourseQueryVo;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 课程表 Service接口
 * @date 2023-11-24 00:26:45
 */
public interface TbCourseService extends IService<TbCourse> {
    IPage<TbCourse> selectPage(Page<TbCourse> pageParam, TbCourseQueryVo queryVo);

    List<TbCourse> queryList(TbCourseQueryVo queryVo);

    public void exportData(HttpServletResponse response);

    public boolean save(TbCourse tbCourse);

    public boolean updateById(TbCourse tbCourse);

    public TbCourse getById(String id);

    public List<TbCourse> getByIds(List<String> ids);
}
