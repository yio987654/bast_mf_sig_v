package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbStudentCourse;
import com.lanf.business.vo.TbStudentCourseQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 学生选课程关系表 Mapper层
 * @date 2023-11-23 16:56:56
 */
@Repository
@Mapper
public interface TbStudentCourseMapper extends BaseMapper<TbStudentCourse> {
    IPage<TbStudentCourse> selectPage(Page<TbStudentCourse> page, @Param("vo") TbStudentCourseQueryVo tbStudentCourseQueryVo);

    List<TbStudentCourse> queryList(@Param("vo") TbStudentCourseQueryVo tbStudentCourseQueryVo);
}