package com.lanf.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lanf.business.model.TbCourse;
import com.lanf.business.vo.TbCourseQueryVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author tanlingfei
 * @version 1.0
 * @description 课程表 Mapper层
 * @date 2023-11-24 00:26:45
 */
@Repository
@Mapper
public interface TbCourseMapper extends BaseMapper<TbCourse> {
    IPage<TbCourse> selectPage(Page<TbCourse> page, @Param("vo") TbCourseQueryVo tbCourseQueryVo);

    List<TbCourse> queryList(@Param("vo") TbCourseQueryVo tbCourseQueryVo);
}